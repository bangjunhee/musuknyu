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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation") //validation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0") //swager
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") //jpa
	implementation("org.springframework.boot:spring-boot-starter-aop") //Spring AOP
	implementation("org.springframework.boot:spring-boot-starter-security") //Spring Security
	implementation("io.jsonwebtoken:jjwt-api:0.12.3") //JWT
	implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta") //queryDsl
	kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta") //queryDsl
	implementation("com.h2database:h2") //h2

	implementation("io.github.microutils:kotlin-logging:1.12.5")  // kotlin-logging

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3") //JWT
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3") //JWT
//	runtimeOnly("org.postgresql:postgresql") //postgresql

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
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