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

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//protobuf {
//    protoc {
//        artifact = "com.google.protobuf:protoc:3.24.4"
//    }
//    plugins {
//        grpc {
//            artifact = 'io.grpc:protoc-gen-grpc-java:1.58.0'
//        }
//    }
//    generateProtoTasks {
//        all().each { task ->
//            task.plugins {
//                grpc {}
//            }
//        }
//    }
//}
