import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val karibudsl_version = "0.7.2"

plugins {
    kotlin("jvm") version "1.3.61"
    id("org.gretty") version "2.3.1"
    war
    id("com.devsoap.vaadin-flow") version "1.2"
}

vaadin {
    version = "14.1.2"
}

defaultTasks("clean", "build")

repositories {
    jcenter()
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
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
    compile("com.vaadin:vaadin-core:${vaadin.version}")
    compile("com.vaadin:flow-server-compatibility-mode:2.1.1")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    compile("org.slf4j:slf4j-simple:1.7.28")

    compile(kotlin("stdlib-jdk8"))

    // test support
    testCompile("com.github.mvysny.kaributesting:karibu-testing-v10:1.1.18")
    testCompile("com.github.mvysny.dynatest:dynatest-engine:0.15")

    // heroku app runner
    staging("com.github.jsimone:webapp-runner-main:9.0.27.1")
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
