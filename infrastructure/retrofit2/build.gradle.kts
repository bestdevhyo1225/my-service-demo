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

    // OkHttp3
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.9")

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    // Resilience4j
    implementation("io.github.resilience4j:resilience4j-spring-boot2:1.7.1")
    implementation("io.github.resilience4j:resilience4j-retrofit:1.7.1")

    // Spring Boot Starter
    implementation("org.springframework.boot:spring-boot-starter")
}
