import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
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
        exceptionFormat = TestExceptionFormat.FULL
    }
}
// solution from https://discuss.gradle.org/t/exclude-files-from-application-plugin-lib-folder/51815/2
// works correctly for production builds and `gradle run` but when running MainKt from Intellij, the vaadin-dev deps are not visible
// on classpath.
val vaadinDevDependency = configurations.dependencyScope("vaadinDevDependency")
val vaadinDevRuntimeClasspath = configurations.resolvable("vaadinDevRuntimeClasspath") {
    extendsFrom(vaadinDevDependency.get())
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named(TargetJvmEnvironment.STANDARD_JVM))
    }
}

dependencies {
    // Karibu-DSL dependency
    implementation(libs.karibu.dsl)

    // Vaadin
    implementation(libs.vaadin.core)
    "vaadinDevDependency"(libs.vaadin.dev)
    implementation(libs.vaadin.boot)

    // logging
    // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
    implementation(libs.slf4j.simple)

    // test support
    testImplementation(libs.karibu.testing)
    testImplementation(libs.junit)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

vaadin {
    productionMode = true
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget = JvmTarget.JVM_21
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass = "com.example.karibudsl.MainKt"
}

tasks.withType<JavaExec> {
    classpath = objects.fileCollection().from(classpath, vaadinDevRuntimeClasspath)
}