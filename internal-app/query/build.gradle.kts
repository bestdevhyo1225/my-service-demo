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
}
