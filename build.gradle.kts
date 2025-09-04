import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// build.gradle.kts
plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.2.0"
    id("org.springframework.boot") version "4.0.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "10.14.0"
    application
}

group = "com.respiroc"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Products"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

kotlin {
    jvmToolchain(21)
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

//    implementation("javax.sql:javax.sql-api")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
