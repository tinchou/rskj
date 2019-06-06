import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://deps.rsklabs.io")
}

dependencies {
    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(project(path = ":rskj-core", configuration = "testRuntime")) {
        exclude(group = "junit", module = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
//    testCompile "org.ethereum:solcJ-all:0.5.7"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
