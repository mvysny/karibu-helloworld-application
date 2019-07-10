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

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA

/**
 * The main view contains a button and a template element.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
class MainView : KComposite() {
    private val root = ui {
        verticalLayout {
            button("Click me") {
                onLeftClick {
                    Notification.show("Clicked!")
                }
            }
        }
    }
}
