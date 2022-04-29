import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("org.springframework.boot") version "2.6.7" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false

    kotlin("jvm") version "1.6.21" apply false
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
    kotlin("plugin.allopen") version "1.6.21" apply false
    kotlin("plugin.noarg") version "1.6.21" apply false
    kotlin("kapt") version "1.6.21" apply false

    java
}

ext["log4j2.version"] = "2.15.0"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")

    group = "com.hs"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.batch:spring-batch-test")
        testImplementation("io.kotest:kotest-assertions-core:5.0.3")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.0.3")
        testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.0.3")
        testImplementation("io.mockk:mockk:1.12.1")
        testImplementation("com.squareup.retrofit2:retrofit-mock:2.9.0")
        testImplementation("com.squareup.okhttp3:okhttp:3.14.9")
        testImplementation("com.squareup.okhttp3:mockwebserver:3.14.9")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xallow-result-return-type")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
