package com.vaadin.flow.demo.helloworld

import com.github.mvysny.kaributesting.v10.*
import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.textfield.TextField

/**
 * Tests the UI. Uses the Browserless testing approach as provided by the
 * [Karibu Testing](https://github.com/mvysny/karibu-testing) library.
 */
class MainViewTest: DynaTest({
    lateinit var routes: Routes
    beforeGroup {
        // Route discovery involves classpath scanning and is an expensive operation.
        // Running the discovery process only once per test run speeds up the test runtime considerably.
        // Discover the routes once and cache the result.
        routes = Routes().autoDiscoverViews("com.vaadin.flow.demo")
    }
    beforeEach {
        // MockVaadin.setup() registers all @Routes, prepares the Vaadin instances for us
        // (the UI, the VaadinSession, VaadinRequest, VaadinResponse, ...) and navigates to the root route.
        MockVaadin.setup(routes)
    }
    afterEach { MockVaadin.tearDown() }

    test("smoke test") {
        // Smoke test is a quick test to check that the basic machinery is in place and works.
        // The analogy would be to turn on an electric device (e.g. a coffee maker)
        // then turn it off immediately without even checking whether it actually works or not,
        // and watch whether there is any smoke. If yes, the coffee maker is
        // probably burning from a short-circuit and any further tests are pointless.

        // The root route should be set directly in the UI; let's check whether it is so.
        // This demoes the direct access to the UI and its children and grand-children,
        // which encompasses all visible Vaadin components.
        val main: MainView = UI.getCurrent().children.findFirst().get() as MainView

        // However when using this kind of low-level lookups, the code quickly gets
        // pretty complicated. Let's use the _get() function instead,
        // which will walk the UI tree for us.
        _expectOne<MainView>()
    }

    test("test greeting") {
        // simulate an user input
        _get<TextField> { caption = "Your name" } ._value = "Martin"

        // simulate a button click as if clicked by the user
        _get<Button> { caption = "Say hello" } ._click()

        // look up the notification and assert on its value
        expectNotifications("Hello, Martin")
    }
})
