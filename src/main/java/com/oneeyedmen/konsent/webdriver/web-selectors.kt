package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.Actor
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import java.net.URI

fun `the page location`(actor: Actor<RemoteWebDriver>): URI = URI.create(actor.driver.currentUrl)

fun `the page content`(actor: Actor<RemoteWebDriver>) = actor.driver.findElementByTagName("body") as RemoteWebElement

fun `the page title`(actor: Actor<RemoteWebDriver>) = actor.driver.title



