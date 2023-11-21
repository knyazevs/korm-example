plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    application
    java
}

group = "s.knyazev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = uri("https://maven.pkg.github.com/knyazevs/korm"))
}

val ktorVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("app.softwork:kotlinx-uuid-core:0.0.21")
    implementation("s.knyazev:core:1.0.6-SNAPSHOT")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("org.flywaydb:flyway-core:10.0.1")
    testImplementation("org.flywaydb:flyway-database-postgresql:10.0.1")
    testImplementation("org.testcontainers:postgresql:1.18.0")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("s.knyazev.example.MainKt")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

