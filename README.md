[![Build Status](https://travis-ci.org/mvysny/karibu10-helloworld-application.svg?branch=master)](https://travis-ci.org/mvysny/karibu-helloworld-application)

# Vaadin 10 Karibu-DSL Example App / Archetype

This project can be used as a starting point to create your own Vaadin Flow application. It has the necessary dependencies and files to get you started.
Just clone this repo and start experimenting! Import it to the IDE of your choice as a Gradle project.
                                              
Uses [Karibu-DSL](https://github.com/mvysny/karibu-dsl) Kotlin bindings to the [Vaadin](https://vaadin.com/flow) framework.

[Online Demo of this app](https://karibu10-helloworld-app.herokuapp.com) running on Heroku.

# Getting Started

To quickly start the app, make sure that you have Java 8 JDK installed. Then, just type this into your terminal:

```bash
git clone https://github.com/mvysny/karibu10-helloworld-application
cd karibu10-helloworld-application
./gradlew appRun
```

**Note**: Java 9 is currently unsupported: `./gradlew appRun` will fail to run. See [issue #1](https://github.com/mvysny/karibu10-helloworld-application/issues/1) for more details. For now please use Java 8 JDK.

The app will be running on [http://localhost:8080/](http://localhost:8080/).

This is a port of [Skeleton Starter Flow](https://github.com/vaadin/skeleton-starter-flow) to Kotlin+Gradle.

# More Resources

* The DSL technique is used to allow you to nest your components in a structured code. This is provided by the
  Karibu-DSL library; please visit the [Karibu-DSL home page](https://github.com/mvysny/karibu-dsl) for more information.
* The browserless testing is demonstrated in the [MainViewTest.kt](src/test/kotlin/com/vaadin/flow/demo/helloworld/MainViewTest.kt) file.
  Please read [Browserless Web Testing](https://github.com/mvysny/karibu-testing) for more information.
* For more complex example which includes multiple pages, please see the [Karibu-DSL example-v10 app](https://github.com/mvysny/karibu-dsl#quickstart-vaadin-10-flow).
  It is a port of the [Beverage Buddy App Starter](https://github.com/vaadin/beverage-starter-flow) to Kotlin + Karibu DSL.
* For information on how to connect the UI to the database backend please visit [Vaadin-on-Kotlin](http://www.vaadinonkotlin.eu/)
  You can find a complete CRUD example at [Vaadin-on-Kotlin vok-example-flow-sql2o](https://github.com/mvysny/vaadin-on-kotlin#vaadin-10-flow-example-project).
