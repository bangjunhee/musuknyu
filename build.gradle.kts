import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.noarg") version "1.8.22"
	kotlin("plugin.allopen") version "1.8.22"
	kotlin("kapt") version "1.8.22"
}

group = "com.sparta"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val queryDslVersion = "5.0.0"
val kotestVersion = "5.5.5"
val mockkVersion = "1.13.8"

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation") //validation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0") //swagger
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") //jpa
	implementation("org.springframework.boot:spring-boot-starter-aop") //Spring AOP
	implementation("org.springframework.boot:spring-boot-starter-security") //Spring Security
	implementation("io.jsonwebtoken:jjwt-api:0.12.3") //JWT
	implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta") //queryDsl
	kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta") //queryDsl
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
	testImplementation("io.mockk:mockk:$mockkVersion")

	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-cache")

	implementation("net.datafaker:datafaker:2.0.2")//dummy

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3") //JWT
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3") //JWT
	// runtimeOnly("org.postgresql:postgresql") //postgresql
	implementation("com.h2database:h2") //h2


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test>().configureEach() {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}


noArg {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}