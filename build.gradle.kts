plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm")
}

group = "dev.kumar"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(kotlin("stdlib-jdk8"))

	implementation ("org.springframework.boot:spring-boot-starter-validation")

	val mapStructVersion = "1.6.3"
	implementation("org.mapstruct:mapstruct:$mapStructVersion")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	implementation("software.amazon.awssdk:s3:2.20.26")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
