import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val vaadinVersion: String by extra
val karibuDslVersion: String by extra

plugins {
    kotlin("jvm") version "1.9.22"
    id("application")
    id("com.vaadin")
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
    implementation("com.github.mvysny.karibudsl:karibu-dsl-v23:$karibuDslVersion")

    // Vaadin
    implementation("com.vaadin:vaadin-core:$vaadinVersion") {
        afterEvaluate {
            if (vaadin.productionMode.get()) {
                exclude(module = "vaadin-dev")
            }
        }
    }
    implementation("com.github.mvysny.vaadin-boot:vaadin-boot:12.2")

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    implementation("org.slf4j:slf4j-simple:2.0.9")

    implementation(kotlin("stdlib-jdk8"))

    // test support
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v24:2.1.1")
    testImplementation("com.github.mvysny.dynatest:dynatest:0.24")
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
