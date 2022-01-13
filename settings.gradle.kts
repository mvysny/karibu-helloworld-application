pluginManagement {
    // also download plugins from the vaadin-prereleases repository.
    // this will allow us to use alpha builds of Vaadin Gradle plugin, since those
    // are not published to https://plugins.gradle.org/plugin/com.vaadin
    repositories {
        maven { setUrl("https://maven.vaadin.com/vaadin-prereleases") }
        gradlePluginPortal()
    }
}
