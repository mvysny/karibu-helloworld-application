import com.vaadin.gradle.getBooleanProperty
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
    alias(libs.plugins.vaadin)
}

defaultTasks("clean", "build")

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        // to see the exceptions of failed tests in Travis-CI console.
        exceptionFormat = TestExceptionFormat.FULL
    }
}

dependencies {
    // Karibu-DSL dependency
    implementation(libs.karibu.dsl)

    // Vaadin
    implementation(libs.vaadin.core) {
        // https://github.com/vaadin/flow/issues/18572
        if (vaadin.productionMode.map { v -> getBooleanProperty("vaadin.productionMode") ?: v }.get()) {
            exclude(module = "vaadin-dev")
        }
    }
    implementation(libs.vaadin.boot)

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    implementation(libs.slf4j.simple)

    implementation(kotlin("stdlib-jdk8"))

    // test support
    testImplementation(libs.karibu.testing)
    testImplementation(libs.dynatest)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = "com.example.karibudsl.MainKt"
}
