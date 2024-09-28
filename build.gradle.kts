import com.github.spotbugs.snom.Effort
import org.springframework.boot.buildpack.platform.build.PullPolicy
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

val umlDoclet: Configuration by configurations.creating

plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    checkstyle
    pmd
    id("com.github.spotbugs") version "6.0.22"
    id("io.freefair.lombok") version "8.10"
}

group = "com.group6"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        vendor = JvmVendorSpec.AMAZON
        languageVersion = JavaLanguageVersion.of(22)
    }
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    umlDoclet("nl.talsmasoftware:umldoclet:2.0.15")
}

configurations {
    umlDoclet
}

ext {
    set("testcontainers.version", "1.20.1")
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

checkstyle {
    toolVersion = "10.18.0"
}

pmd {
    toolVersion = "7.6.0"
    isConsoleOutput = true
    ruleSetFiles = files("config/pmd/custom-ruleset.xml")
}

spotbugs {
    effort = Effort.MAX
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
