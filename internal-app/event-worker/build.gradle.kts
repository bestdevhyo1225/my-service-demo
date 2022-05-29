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
    implementation(project(":infrastructure:retrofit2"))

    // Spring Boot Stater
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}
