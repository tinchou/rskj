package org.ethereum.rpc;

import co.rsk.rpc.CorsConfiguration;
import co.rsk.rpc.JsonRpcFilterServer;
import co.rsk.rpc.ModuleDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.googlecode.jsonrpc4j.AnnotationsErrorResolver;
import com.googlecode.jsonrpc4j.DefaultErrorResolver;
import com.googlecode.jsonrpc4j.DefaultHttpStatusCodeProvider;
import com.googlecode.jsonrpc4j.MultipleErrorResolver;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.ethereum.rpc.exception.RskErrorResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ChannelHandler.Sharable
public class JsonRpcWeb3ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger("jsonrpc");
    private static final int JSON_RPC_SERVER_ERROR_HIGH_CODE = -32099;

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
    private final JsonRpcFilterServer jsonRpcServer;
    private final CorsConfiguration corsConfiguration;

    public JsonRpcWeb3ServerHandler(Web3 service, List<ModuleDescription> filteredModules, CorsConfiguration corsConfiguration) {
        this.corsConfiguration = corsConfiguration;
        this.jsonRpcServer = new JsonRpcFilterServer(service, service.getClass(), filteredModules);
        jsonRpcServer.setErrorResolver(new MultipleErrorResolver(new RskErrorResolver(), AnnotationsErrorResolver.INSTANCE, DefaultErrorResolver.INSTANCE));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HttpMethod httpMethod = request.getMethod();
        HttpResponse response;
        if (HttpMethod.OPTIONS.equals(httpMethod)) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers()
                .add(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_METHODS, String.join(", ", HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.OPTIONS.name()))
                .add(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.Names.CONTENT_TYPE)
                .add(HttpHeaders.Names.ACCESS_CONTROL_MAX_AGE, String.valueOf(600))
                .add(HttpHeaders.Names.ALLOW, String.join(", ", HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.TRACE.name(), HttpMethod.OPTIONS.name()));
        } else if (HttpMethod.POST.equals(httpMethod)) {
            ByteBuf content = Unpooled.buffer();
            HttpResponseStatus responseStatus = HttpResponseStatus.OK;
            try (ByteBufOutputStream os = new ByteBufOutputStream(content);
                 ByteBufInputStream is = new ByteBufInputStream(request.content())){
                int result = jsonRpcServer.handleRequest(is, os);
                responseStatus = HttpResponseStatus.valueOf(DefaultHttpStatusCodeProvider.INSTANCE.getHttpStatusCode(result));
            } catch (Exception e) {
                LOGGER.error("Unexpected error", e);
                content = buildErrorContent(JSON_RPC_SERVER_ERROR_HIGH_CODE, e.getMessage());
                responseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;
            } finally {
                response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    responseStatus,
                    content
                );
            }
        } else {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_IMPLEMENTED);
        }
        if (corsConfiguration.hasHeader()) {
            response.headers().add(HttpHeaders.newEntity(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN), this.corsConfiguration.getHeader());
            response.headers().add(HttpHeaders.newEntity(HttpHeaders.Names.VARY), HttpHeaders.Names.ORIGIN);
        }
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private ByteBuf buildErrorContent(int errorCode, String errorMessage) throws JsonProcessingException {
        Map<String, JsonNode> errorProperties = new HashMap<>();
        errorProperties.put("code", jsonNodeFactory.numberNode(errorCode));
        errorProperties.put("message", jsonNodeFactory.textNode(errorMessage));
        JsonNode error = jsonNodeFactory.objectNode().set("error", jsonNodeFactory.objectNode().setAll(errorProperties));
        return Unpooled.wrappedBuffer(mapper.writeValueAsBytes(mapper.treeToValue(error, Object.class)));
    }
}
