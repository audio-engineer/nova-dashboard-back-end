import com.github.spotbugs.snom.Effort
import org.springframework.boot.buildpack.platform.build.PullPolicy
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

/**
 * UMLDoclet configuration object.
 */
val umlDoclet: Configuration by configurations.creating

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    checkstyle
    pmd
    id("com.github.spotbugs") version "6.0.25"
    id("io.freefair.lombok") version "8.10.2"
    jacoco
}

group = "com.group6.nova.dashboard"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        vendor = JvmVendorSpec.AMAZON
        languageVersion = JavaLanguageVersion.of(23)
    }
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.mapstruct:mapstruct-processor:latest.release")
    implementation("org.mapstruct:mapstruct:latest.release")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("uk.gov.nationalarchives:csv-validator-java-api:latest.release")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testAndDevelopmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:latest.release")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    umlDoclet("nl.talsmasoftware:umldoclet:latest.release")
}

checkstyle {
    toolVersion = "10.19.0"
}

pmd {
    toolVersion = "7.7.0"
    ruleSetFiles = files("config/pmd/custom-ruleset.xml")
}

spotbugs {
    effort = Effort.MAX
}

jacoco {
    toolVersion = "0.8.12"
}

configurations {
    umlDoclet
}

ext {
    set("testcontainers.version", "1.20.3")
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    pullPolicy.set(PullPolicy.IF_NOT_PRESENT)
//    buildpacks.set(
//        listOf(
//            "gcr.io/paketo-buildpacks/amazon-corretto:latest"
//        )
//    )
}

tasks.javadoc {
    source = sourceSets.main.get().allJava

    val docletOptions = options as StandardJavadocDocletOptions

    docletOptions.docletpath = umlDoclet.files.toList()
    docletOptions.doclet = "nl.talsmasoftware.umldoclet.UMLDoclet"
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
    }

    reports.create("xml") {
        required = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    systemProperty("spring.profiles.active", "test")

    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required = true
    }
}
