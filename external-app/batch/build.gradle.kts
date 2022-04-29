plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Project
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":infrastructure:mongodb"))
    implementation(project(":infrastructure:redis"))

    // Spring Boot Starter
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}
