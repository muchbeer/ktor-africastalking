val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
}

group = "com.africastalking"
version = "0.0.1"
application {
    mainClass.set("com.africastalking.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks {
    create("stage").dependsOn("installDist")
}

repositories {
    mavenCentral()

}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation ("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation ("io.ktor:ktor-serialization-jackson:$ktor_version")

    implementation ("mysql:mysql-connector-java:8.0.29")
    implementation ("org.ktorm:ktorm-core:3.5.0")
    implementation ("org.ktorm:ktorm-support-mysql:3.5.0")

    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.json:json:20220320")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

//    implementation("io.ktor:ktor-server-auth:$ktor_version")
 //   implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

}