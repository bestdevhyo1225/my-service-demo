import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Project
    implementation(project(":common"))
    implementation(project(":domain"))

    // Spring Boot Starter Data Redis Reactive
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // Reactor Kotlin Extensions
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
}
