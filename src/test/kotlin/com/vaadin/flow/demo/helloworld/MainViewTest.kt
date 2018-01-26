package com.vaadin.flow.demo.helloworld

import com.github.karibu.testing.MockVaadin
import com.github.karibu.testing._click
import com.github.karibu.testing._get
import com.github.karibu.testing.autoDiscoverViews
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.textfield.TextField
import org.junit.Before
import org.junit.Test
import kotlin.test.expect

class MainViewTest {
    @Before
    fun mock() {
        MockVaadin.setup(autoDiscoverViews("com.vaadin.flow.demo"))
    }

    @Test
    fun testGreeting() {
        _get<Button> { caption = "Click me" } ._click()
        expect("Clicked!") { _get<ExampleTemplate>().value }
    }
}
