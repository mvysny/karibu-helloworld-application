package com.example.karibudsl

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme

@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@Theme("my-theme")
class AppShell: AppShellConfigurator

/**
 * Run this function to launch your app in Embedded Jetty.
 */
fun main(vararg args: String) {
    VaadinBoot().run()
}
