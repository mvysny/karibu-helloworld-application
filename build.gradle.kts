import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.gretty") version "3.0.6"
    war
    id("com.vaadin") version "21.0.6"
}

val karibudsl_version = "1.1.1"
val vaadin_version = "21.0.6"

defaultTasks("clean", "build")

repositories {
    mavenCentral()
    maven { setUrl("https://maven.vaadin.com/vaadin-addons") }
    maven { setUrl("https://maven.vaadin.com/vaadin-prereleases") }
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
    // managedClassReload = true // temporarily disabled because of https://github.com/gretty-gradle-plugin/gretty/issues/166
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
    implementation("com.github.mvysny.karibudsl:karibu-dsl:$karibudsl_version")
    implementation("com.github.mvysny.karibu-tools:karibu-tools:0.7")

    // Vaadin
    implementation("com.vaadin:vaadin-core:${vaadin_version}")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    implementation("org.slf4j:slf4j-simple:1.7.32")

    implementation(kotlin("stdlib-jdk8"))

    // test support
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10:1.3.7")
    testImplementation("com.github.mvysny.dynatest:dynatest:0.22")

    // heroku app runner
    staging("com.heroku:webapp-runner-main:9.0.52.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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

vaadin {
    if (gradle.startParameter.taskNames.contains("stage")) {
        productionMode = true
    }
}
