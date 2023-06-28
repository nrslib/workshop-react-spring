import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.9-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.google.cloud.tools.jib") version "3.3.0"
    id("de.undercouch.download").version("3.4.3")
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    group = "com.nrslib.workshop"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven("https://jitpack.io")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project (":workshop-service") {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "com.google.cloud.tools.jib")
    apply(plugin = "de.undercouch.download")

    dependencies {
        // Default
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("io.arrow-kt:arrow-core:1.1.2")
        implementation("com.amazonaws:aws-java-sdk-opensdk:1.12.381")
        implementation("com.amazonaws:aws-xray-recorder-sdk-core:2.13.0")
        implementation("com.amazonaws:aws-xray-recorder-sdk-spring:2.13.0")
        implementation("com.amazonaws:aws-xray-recorder-sdk-slf4j:2.13.0")
        implementation("com.amazonaws:aws-xray-recorder-sdk-sql-mysql:2.13.0")
        implementation("org.apache.kafka:kafka-clients:3.2.2")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

        runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.11")
        runtimeOnly("com.h2database:h2:2.1.214")
        runtimeOnly("mysql:mysql-connector-java:8.0.32")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation("com.ninja-squad:springmockk:3.1.1")
    }

    jib {
        from {
            image = "adoptopenjdk/openjdk11"
        }
        to {
            image = project.property("jib.to.image") as String
            setCredHelper("ecr-login")
        }
    }
}