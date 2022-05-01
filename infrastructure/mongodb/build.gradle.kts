import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.allopen")
    kotlin("plugin.spring")
}

allOpen {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

dependencies {
    // Project
    implementation(project(":common"))
    implementation(project(":domain"))

    // Spring Boot Data MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}
