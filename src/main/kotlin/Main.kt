package com.example.karibudsl

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.dependency.StyleSheet
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo

@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@StyleSheet(Lumo.STYLESHEET)
@StyleSheet("styles.css")
class AppShell : AppShellConfigurator

/**
 * Run this function to launch your app in Embedded Jetty.
 */
fun main() {
    VaadinBoot().run()
}
