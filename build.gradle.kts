import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val karibudsl_version = "0.7.0"

plugins {
    kotlin("jvm") version "1.3.50"
    id("org.gretty") version "2.3.1"
    war
    id("com.devsoap.vaadin-flow") version "1.3.beta2"
}

vaadin {
    version = "14.0.7"
}

defaultTasks("clean", "build")

repositories {
    vaadin.repositories()
    vaadin.prereleases()
}

gretty {
    contextPath = "/"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        // to see the exceptions of failed tests in Travis-CI console.
        exceptionFormat = TestExceptionFormat.FULL
    }
}

val staging by configurations.creating

dependencies {
    // Karibu-DSL dependency
    compile("com.github.mvysny.karibudsl:karibu-dsl-v10:$karibudsl_version")

    // Vaadin 14
    compile(vaadin.bom())
    compile(vaadin.core())

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    compile(vaadin.slf4j())

    compile(kotlin("stdlib-jdk8"))

    // test support
    testCompile("com.github.mvysny.kaributesting:karibu-testing-v10:1.1.13")
    testCompile("com.github.mvysny.dynatest:dynatest-engine:0.15")

    // heroku app runner
    staging("com.github.jsimone:webapp-runner-main:9.0.24.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// Heroku
tasks {
    val copyToLib by registering(Copy::class) {
        into("$buildDir/server")
        from(staging) {
            include("webapp-runner*")
        }
    }
    val stage by registering {
        dependsOn("build", copyToLib)
    }
}
