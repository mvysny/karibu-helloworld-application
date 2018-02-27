package com.vaadin.flow.demo.helloworld

import com.github.karibu.testing.MockVaadin
import com.github.karibu.testing._click
import com.github.karibu.testing._get
import com.github.karibu.testing.autoDiscoverViews
import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.button.Button
import kotlin.test.expect

/**
 * Tests the UI. Uses the Serverless testing approach as provided by the [Karibu Testing](https://github.com/mvysny/karibu-testing) library.
 */
class MainViewTest: DynaTest({
    beforeEach { MockVaadin.setup(autoDiscoverViews("com.vaadin.flow.demo")) }

    test("test greeting") {
        // MockVaadin.setup() discovered all @Routes and prepared the UI for us; we can now read components from it.
        // simulate a button click as if clicked by the user
        _get<Button> { caption = "Click me" } ._click()

        // look up the Example Template and assert on its value
        expect("Clicked!") { _get<ExampleTemplate>().value }
    }
})
