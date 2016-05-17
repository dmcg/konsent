package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.Actor
import com.oneeyedmen.konsent.selector
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import java.net.URI

fun <ResultT> webSelector(description: String, block: Actor<RemoteWebDriver>.() -> ResultT) = selector(description, block)

val `the page location` = webSelector("the page location") {
    URI.create(driver.currentUrl)
}

val `the page content` = webSelector("the page content") {
    driver.findElementByTagName("body") as RemoteWebElement?
}

val `the page title` = webSelector("the page title") {
    driver.title
}



