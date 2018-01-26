/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.helloworld

import com.github.vok.karibudsl.flow.VaadinDsl
import com.github.vok.karibudsl.flow.init
import com.vaadin.flow.demo.helloworld.ExampleTemplate.ExampleModel
import com.vaadin.flow.templatemodel.TemplateModel
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.polymertemplate.PolymerTemplate

/**
 * Simple template example.
 */
@Tag("example-template")
@HtmlImport("frontend://ExampleTemplate.html")
class ExampleTemplate : PolymerTemplate<ExampleModel>() {

    /**
     * Template model which defines the single "value" property.
     */
    interface ExampleModel : TemplateModel {
        var value: String
    }

    var value: String
    get() = model.value
    set(value) { model.value = value }

    init {
        // Set the initial value to the "value" property.
        value = "Not clicked"
    }
}

fun (@VaadinDsl HasComponents).exampleTemplate(block: (@VaadinDsl ExampleTemplate).() -> Unit = {})
        = init(ExampleTemplate(), block)
