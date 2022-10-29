[![Powered By Vaadin on Kotlin](http://vaadinonkotlin.eu/iconography/vok_badge.svg)](http://vaadinonkotlin.eu)
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/mvysny/karibu10-helloworld-application)
[![Build Status](https://github.com/mvysny/karibu10-helloworld-application/actions/workflows/gradle.yml/badge.svg)](https://github.com/mvysny/karibu10-helloworld-application/actions/workflows/gradle.yml)

# Hello, Vaadin 👋

A simple "Hello, World" application in Vaadin 23 and Kotlin. Uses [Vaadin Boot](https://github.com/mvysny/vaadin-boot).

This project can be used as a starting point to create your own Vaadin application.
It has the necessary dependencies and files to get you started.
Just clone this repo and start experimenting! Import it to the IDE of your choice as a Gradle project.

[MainView.kt](src/main/kotlin/com/example/karibudsl/MainView.kt):

```kotlin
verticalLayout(classNames = "centered-content") {
    textField("Your name")
    button("Say hello") {
        setPrimary(); addClickShortcut(Key.ENTER)
    }
}
```

Uses [Karibu-DSL](https://github.com/mvysny/karibu-dsl) Kotlin bindings for the [Vaadin](https://vaadin.com) framework.

> Note: this example project uses Gradle. For Maven-based example project please visit
> [karibu10-helloworld-application-maven](https://github.com/mvysny/karibu10-helloworld-application-maven).

# Documentation

Please see the [Vaadin Boot](https://github.com/mvysny/vaadin-boot#preparing-environment) documentation
on how you run, develop and package this Vaadin-Boot-based app.

# More Resources

* The DSL technique is used to allow you to nest your components in a structured code. This is provided by the
  Karibu-DSL library; please visit the [Karibu-DSL home page](https://github.com/mvysny/karibu-dsl) for more information.
* The browserless testing is demonstrated in the [MainViewTest.kt](src/test/kotlin/com/vaadin/flow/demo/helloworld/MainViewTest.kt) file.
  Please read [Browserless Web Testing](https://github.com/mvysny/karibu-testing) for more information.
* For more complex example which includes multiple pages, please see the [Beverage Buddy Karibu-DSL example-v10 app](https://github.com/mvysny/karibu-dsl#quickstart-vaadin-10-flow).
  It is a port of the Vaadin official Java [Beverage Buddy App Starter](https://github.com/vaadin/beverage-starter-flow) to Kotlin + Karibu DSL.
* For information on how to connect the UI to the database backend please visit [Vaadin-on-Kotlin](http://www.vaadinonkotlin.eu/)
  You can find a complete CRUD example at [Vaadin-on-Kotlin vok-example-flow-sql2o](https://github.com/mvysny/vaadin-on-kotlin#vaadin-10-flow-example-project).
