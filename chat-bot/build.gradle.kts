import com.google.protobuf.gradle.id

plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.13.1"

    id ("com.google.protobuf") version "0.9.4"
}

group = "kg.kazbekov"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:1.49.2"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins{id("grpc")
            }
        }
    }
}

tasks.withType<JavaCompile>{
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
