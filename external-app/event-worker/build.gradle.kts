plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Project
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":infrastructure:jpa"))
    implementation(project(":infrastructure:mongodb"))
    implementation(project(":infrastructure:redis"))

    // Spring Boot Stater
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}
