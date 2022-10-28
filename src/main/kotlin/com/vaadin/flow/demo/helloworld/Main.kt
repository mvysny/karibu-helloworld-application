package com.vaadin.flow.demo.helloworld

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA

@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
class AppShell: AppShellConfigurator

fun main(vararg args: String) {
    VaadinBoot().withArgs(args).run()
}
