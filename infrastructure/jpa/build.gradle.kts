import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
    kotlin("plugin.jpa")
    kotlin("plugin.noarg")
    kotlin("plugin.spring")
}

allOpen {
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
}

dependencies {
    // Project
    implementation(project(":domain"))

    // Spring Boot Data Jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Querydsl 5.0.0
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")

    // MySQL Connector
    runtimeOnly("mysql:mysql-connector-java")

    // H2 Database
    runtimeOnly("com.h2database:h2")
}

sourceSets["main"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/querydsl")
}
