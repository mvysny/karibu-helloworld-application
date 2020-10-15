[![Powered By Vaadin on Kotlin](http://vaadinonkotlin.eu/iconography/vok_badge.svg)](http://vaadinonkotlin.eu)
[![Heroku](https://heroku-badge.herokuapp.com/?app=karibu10-helloworld-app&style=flat&svg=1)](https://karibu10-helloworld-app.herokuapp.com/)
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/mvysny/karibu10-helloworld-application)

# Hello, Vaadin 👋

A simple "Hello, World" application in Vaadin 14 and Kotlin.

This project can be used as a starting point to create your own Vaadin 14+ application.
It has the necessary dependencies and files to get you started.
Just clone this repo and start experimenting! Import it to the IDE of your choice as a Gradle project.

[MainView.kt](src/main/kotlin/com/vaadin/flow/demo/helloworld/MainView.kt):

```kotlin
verticalLayout(classNames = "centered-content") {
    textField("Your name")
    button("Say hello") {
        setPrimary(); addClickShortcut(Key.ENTER)
    }
}
```
                                         
Uses [Karibu-DSL](https://github.com/mvysny/karibu-dsl) Kotlin bindings for the [Vaadin](https://vaadin.com) framework.

[Online Demo of this app](https://karibu10-helloworld-app.herokuapp.com) running on Heroku.

> Note: this example project uses Gradle. For Maven-based example project please visit
> [karibu10-helloworld-application-maven](https://github.com/mvysny/karibu10-helloworld-application-maven).

# Preparing Environment

Java 8 (or higher) JDK is required.

Vaadin 14 apps also require nodejs and pnpm, but these tools are installed automatically for you
when you build the app for the first time via Gradle.
Alternatively, you can install nodejs and npm to your OS:

* Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
* Linux: `sudo apt install npm`

## Getting Started

To quickly start the app, just type this into your terminal:

```bash
git clone https://github.com/mvysny/karibu10-helloworld-application
cd karibu10-helloworld-application
./gradlew appRun
```

Gradle will automatically download an embedded servlet container (Jetty) and will run your app in it. Your app will be running on
[http://localhost:8080](http://localhost:8080).

Since the build system is a Gradle file written in Kotlin, we suggest you
use [Intellij IDEA](https://www.jetbrains.com/idea/download)
to edit the project files. The Community edition is enough to run the server
via Gretty's `./gradlew appRun`. The Ultimate edition will allow you to run the
project in Tomcat - this is the recommended
option for a real development.

> This is a port of [Skeleton Starter Flow](https://github.com/vaadin/skeleton-starter-flow) to Kotlin+Gradle.

## Supported Modes

Runs in Vaadin 14 npm mode, using the [Vaadin Gradle Plugin](https://github.com/vaadin/vaadin-gradle-plugin).

Both the [development and production modes](https://vaadin.com/docs/v14/flow/production/tutorial-production-mode-basic.html) are supported.
To prepare for development mode, just run:

```bash
./gradlew clean vaadinPrepareFrontend
```

To build in production mode, just run:

```bash
./gradlew clean build -Pvaadin.productionMode
```

Vaadin will download nodejs and npm/pnpm automatically for you (handy for CI).

# Workflow

To compile the entire project in production mode, run `./gradlew -Pvaadin.productionMode`.

To run the application in development mode, run `./gradlew appRun` and open [http://localhost:8080/](http://localhost:8080/).

To produce a deployable production-mode WAR:
- run `./gradlew -Pvaadin.productionMode`
- You will find the WAR file in `build/libs/*.war`
- To revert your environment back to development mode, just run `./gradlew` or `./gradlew vaadinPrepareFrontend`
  (omit the `-Pvaadin.productionMode`) switch.

This will allow you to quickly start the example app and allow you to do some basic modifications.

## Development with Intellij IDEA Ultimate

The easiest way (and the recommended way) to develop Karibu-DSL-based web applications is to use Intellij IDEA Ultimate.
It includes support for launching your project in any servlet container (Tomcat is recommended)
and allows you to debug the code, modify the code and hot-redeploy the code into the running Tomcat
instance, without having to restart Tomcat.

1. First, download Tomcat and register it into your Intellij IDEA properly: https://www.jetbrains.com/help/idea/2017.1/defining-application-servers-in-intellij-idea.html
2. Then just open this project in Intellij, simply by selecting `File / Open...` and click on the
   `build.gradle` file. When asked, select "Open as Project".
2. You can then create a launch configuration which will launch this example app in Tomcat with Intellij: just
   scroll to the end of this tutorial: https://kotlinlang.org/docs/tutorials/httpservlets.html
3. Start your newly created launch configuration in Debug mode. This way, you can modify the code
   and press `Ctrl+F9` to hot-redeploy the code. This only redeploys java code though, to
   redeploy resources just press `Ctrl+F10` and select "Update classes and resources"
   
Or watch the [Debugging Vaadin Apps With Intellij video](https://www.youtube.com/watch?v=M0Q7D03bYXc).

## Dissection of project files

Let's look at all files that this project is composed of, and what are the points where you'll add functionality:

| Files | Meaning
| ----- | -------
| [build.gradle.kts](build.gradle.kts) | [Gradle](https://gradle.org/) build tool configuration files. Gradle is used to compile your app, download all dependency jars and build a war file
| [gradlew](gradlew), [gradlew.bat](gradlew.bat), [gradle/](gradle) | Gradle runtime files, so that you can build your app from command-line simply by running `./gradlew`, without having to download and install Gradle distribution yourself.
| [.travis.yml](.travis.yml) | Configuration file for [Travis-CI](http://travis-ci.org/) which tells Travis how to build the app. Travis watches your repo; it automatically builds your app and runs all the tests after every commit.
| [.gitignore](.gitignore) | Tells [Git](https://git-scm.com/) to ignore files that can be produced from your app's sources - be it files produced by Gradle, Intellij project files etc.
| [src/main/resources/](src/main/resources) | A bunch of static files not compiled by Kotlin in any way; see below for explanation.
| [simplelogger.properties](src/main/resources/simplelogger.properties) | We're using [Slf4j](https://www.slf4j.org/) for logging and this is the configuration file for [Slf4j Simple Logger](https://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html).
| [webapp/](src/main/webapp) | static files provided as-is to the browser.
| [src/main/kotlin/](src/main/kotlin) | The main Kotlin sources of your web app. You'll be mostly editing files located in this folder.
| [MainView.kt](src/main/kotlin/com/vaadin/flow/demo/helloworld/MainView.kt) | When Servlet Container (such as [Tomcat](http://tomcat.apache.org/)) starts your app, it will show the components attached to the main route, in this case, the `MainView` class.
| [MainViewTest.kt](src/test/kotlin/com/vaadin/flow/demo/helloworld/MainViewTest.kt) | Automatically run by Gradle to test your UI; see [Karibu Testing](https://github.com/mvysny/karibu-testing) for more information.

# GitPod

A preliminary support for editing the project in GitPod is available. Visit the
[karibu10-helloworld-app on GitPod](https://gitpod.io/#https://github.com/mvysny/karibu10-helloworld-application)
to start. Please wait a bit to allow GitPod to compile the project and download all files.
The app preview will be started automatically.

Couple of tips:
* Modify `src/main/kotlin/com/vaadin/flow/demo/helloworld/MainView.kt` to toy with the project.
* The Gretty/Gradle code will hot-reload changes in the `MainView.kt` automatically after a couple of seconds.
* If the hot-reload fails and you see any nasty java `IllegalAccessErrors`, or if you
  start getting `ERROR dev-webpack` errors, just kill Gretty and start it from scratch:
   * Focus the "/workspace/karibu10-helloworld-application" tab at the bottom, where the `:appRun` task is running
   * Press `Ctrl+C` to kill Gradle+Gretty
   * Run `./gradlew appRun` to recompile and relaunch your project
   * In the "PREVIEW" window, reload the page.
   * See [Issue 63](https://github.com/vaadin/vaadin-gradle-plugin/issues/63) for more details.

# Docker

Use the following `Dockerfile` to both build the app, and to create a final production container:

```dockerfile
# Build stage
FROM openjdk:11 AS BUILD
RUN git clone https://github.com/mvysny/karibu10-helloworld-application /app
WORKDIR app
RUN ./gradlew -Pvaadin.productionMode

# Run stage
FROM tomcat:9.0.35-jdk11-openjdk
COPY --from=BUILD /app/build/libs/app.war /usr/local/tomcat/webapps/ROOT.war
```

To build the docker image:
```bash
docker build -t mvy/karibu10:latest .
```

To run the app:
```bash
docker run --rm -ti -p 8080:8080 mvy/karibu10
```

Now open [http://localhost:8080/](http://localhost:8080/).

# More Resources

* The DSL technique is used to allow you to nest your components in a structured code. This is provided by the
  Karibu-DSL library; please visit the [Karibu-DSL home page](https://github.com/mvysny/karibu-dsl) for more information.
* The browserless testing is demonstrated in the [MainViewTest.kt](src/test/kotlin/com/vaadin/flow/demo/helloworld/MainViewTest.kt) file.
  Please read [Browserless Web Testing](https://github.com/mvysny/karibu-testing) for more information.
* For more complex example which includes multiple pages, please see the [Beverage Buddy Karibu-DSL example-v10 app](https://github.com/mvysny/karibu-dsl#quickstart-vaadin-10-flow).
  It is a port of the Vaadin official Java [Beverage Buddy App Starter](https://github.com/vaadin/beverage-starter-flow) to Kotlin + Karibu DSL.
* For information on how to connect the UI to the database backend please visit [Vaadin-on-Kotlin](http://www.vaadinonkotlin.eu/)
  You can find a complete CRUD example at [Vaadin-on-Kotlin vok-example-flow-sql2o](https://github.com/mvysny/vaadin-on-kotlin#vaadin-10-flow-example-project).
