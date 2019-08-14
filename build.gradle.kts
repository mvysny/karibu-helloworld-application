import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val karibudsl_version = "0.7.0"

plugins {
    kotlin("jvm") version "1.3.41"
    id("org.gretty") version "2.3.1"
    war
    id("com.devsoap.vaadin-flow") version "1.2"
}

vaadin {
    version = "14.0.0"
}

defaultTasks("clean", "build")

repositories {
    jcenter()
    maven { setUrl("https://maven.vaadin.com/vaadin-prereleases/") }
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
    // Karibu-DSL dependency, includes Vaadin
    compile("com.github.mvysny.karibudsl:karibu-dsl-v10:$karibudsl_version")
    compile("com.vaadin:flow-server-compatibility-mode:2.0.7")
    compile("com.vaadin:vaadin-core:${vaadin.version}")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")

    // logging
    // currently we are logging through the SLF4J API to LogBack. See src/main/resources/logback.xml file for the logger configuration
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("org.slf4j:slf4j-api:1.7.25")

    compile(kotlin("stdlib-jdk8"))

    // test support
    testCompile("com.github.mvysny.kaributesting:karibu-testing-v10:1.1.9")
    testCompile("com.github.mvysny.dynatest:dynatest-engine:0.15")

    // heroku app runner
    staging("com.github.jsimone:webapp-runner-main:9.0.22.0")
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
