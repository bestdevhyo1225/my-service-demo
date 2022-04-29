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
