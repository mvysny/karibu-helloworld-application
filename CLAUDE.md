# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

A minimal "Hello, World" Vaadin Flow application written in Kotlin. It uses Karibu-DSL for declarative UI construction and [Vaadin Boot](https://github.com/mvysny/vaadin-boot) to run the app from a plain `main()` via embedded Jetty — **no Spring/Spring Boot involvement**. Intended as a starting template for new Vaadin-on-Kotlin projects.

Requires JDK 21+. CI matrix tests against JDK 21 and 25.

## Common commands

- `./gradlew build` — compile, run tests, and assemble the distribution.
- `./gradlew test` — run JUnit 5 tests.
- `./gradlew test --tests com.example.karibudsl.MainViewTest.testGreeting` — run a single test.
- `./gradlew run` — launch the app locally on http://localhost:8080 (dev mode, with Vaadin dev server).
- `./gradlew clean build -Pvaadin.productionMode` — production build. The `-Pvaadin.productionMode` flag bundles the frontend and drops `vaadin-dev`; this is also what CI and the Docker build use.
- `./gradlew build -Pvaadin.productionMode && cd build/distributions && tar xvf app.tar && ./app/bin/app` — run the production distribution locally.
- `docker build -t test/karibu-helloworld-application:latest . && docker run --rm -ti -p8080:8080 test/karibu-helloworld-application` — containerized production build/run.

`defaultTasks` is set to `clean build`, so a bare `./gradlew` runs both.

## Architecture

Three source files make up the whole app — the small size is the point, but the pieces below are load-bearing when extending it.

- **`Main.kt`** — entry point. `main()` calls `VaadinBoot().run()` which starts embedded Jetty and serves the Vaadin app. The `AppShell` class (annotated `@PWA` + `@StyleSheet`) is Vaadin's mechanism for configuring the outer HTML shell and registering global stylesheets; there is no `web.xml` or Spring context.
- **`MainView.kt`** — a `@Route("")` view extending `KComposite` (Karibu-DSL's composite base). The DSL pattern used here: UI is built inside a `ui { ... }` block assigned to a `root` property; behavior (click listeners etc.) is wired in `init {}`. Keep UI construction and event handlers separated the way `MainView` does — it's idiomatic for Karibu-DSL.
- **`MainViewTest.kt`** — browserless UI tests via Karibu-Testing. Route discovery (`Routes().autoDiscoverViews(...)`) is done **once** in `@BeforeAll` and cached because classpath scanning is expensive; `MockVaadin.setup(routes)` / `tearDown()` runs per test. New views under `com.example.karibudsl` are picked up automatically by `autoDiscoverViews`. Lookups use `_get<T> { ... }` / `_expectOne<T>()`; simulate input via `._value = ...` and clicks via `._click()`; assert notifications via `expectNotifications(...)`.

### Dependency management

Versions live in `gradle/libs.versions.toml` (Gradle version catalog). Notable pinning:
- `karibu-testing` is `karibu-testing-v24` and `karibu-dsl` is `karibu-dsl-v23` — these `-vNN` suffixes track the **Vaadin** major version they support, not the library's own version. When bumping Vaadin, check that the matching Karibu artifacts exist.
- `vaadin-dev` is only added when `!vaadin.effective.productionMode.get()`, so production builds don't ship the dev bundle.

### Static resources

- `src/main/resources/webapp/` — served at web root. `ROOT/` contains static files; `styles.css` is referenced by the `@StyleSheet("styles.css")` on `AppShell`.
- `src/main/resources/simplelogger.properties` — SLF4J-Simple configuration. The app logs via SLF4J → slf4j-simple (no Logback/Log4j).

## Gotchas

- Do not add Spring dependencies — this app deliberately uses Vaadin Boot's plain-Jetty model. Spring-specific Vaadin features (e.g. `@SpringComponent`, security config) won't apply.
- New views must live in the `com.example.karibudsl` package (or a subpackage) to be found by `autoDiscoverViews` in tests. If you move the package, update the test too.
- Karibu-Testing requires `MockVaadin.setup()` before any UI interaction and `tearDown()` after — missing either causes confusing failures in subsequent tests.
