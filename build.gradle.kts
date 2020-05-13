import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val jUnitVersion: String by project
val hamcrestVersion: String by project
val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val stringUtilsVersion: String by project
val commonsIOVersion: String by project

plugins {
    kotlin("jvm") version "1.3.70"
}

allprojects {
    group = "com.test.domicilios"
    project.version = "1.0"

    apply(plugin = "org.jetbrains.kotlin.jvm")

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    repositories {
        jcenter()
        mavenCentral()
        maven(url = "http://dl.bintray.com/ijabz/maven")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
        }
    }

    dependencies {
        // Kotlin
        implementation(kotlin(module = "stdlib-jdk8", version = kotlinVersion))
        implementation(kotlin(module = "reflect", version = kotlinVersion))

        // Util
        implementation("commons-io:commons-io:$commonsIOVersion")

        // Logging
        implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        // Test
        testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
        testImplementation("org.hamcrest:hamcrest-core:$hamcrestVersion")
        testImplementation("org.apache.commons:commons-lang3:$stringUtilsVersion")
    }
}
