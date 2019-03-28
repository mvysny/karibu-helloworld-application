package com.vaadin.flow.demo.helloworld

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.init
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.dependency.HtmlImport
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Tag("my-test-element")
@HtmlImport("bower_components/my-test-element/my-test-element.html")
class MyTestComponent : Component() {
    var userName: String by ElementStringProperty()
}

@VaadinDsl
fun (@VaadinDsl HasComponents).myTestComponent(userName: String = "", block: (@VaadinDsl MyTestComponent).() -> Unit = {}) = init(MyTestComponent().apply {
    this.userName = userName
}, block)

class ElementStringProperty(val defaultValue: String = "") : ReadWriteProperty<Component, String>, Serializable {
    override fun getValue(thisRef: Component, property: KProperty<*>): String = thisRef.element.getProperty(property.name, defaultValue)
    override fun setValue(thisRef: Component, property: KProperty<*>, value: String) {
        thisRef.element.setProperty(property.name, value)
    }
}
