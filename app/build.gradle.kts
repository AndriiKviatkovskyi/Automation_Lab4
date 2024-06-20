plugins {
    id("java")
}

group = "org.kviat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(mapOf("path" to ":compile")))
    annotationProcessor(project(mapOf("path" to ":compile")))
    implementation (project(":annotations"))
    annotationProcessor (project(":compile"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}