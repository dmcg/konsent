package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.selector
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import java.net.URI

val `the page location` = selector<URI, RemoteWebDriver>("the page location") {
    URI.create(driver.currentUrl)
}

val `the page content` = selector<RemoteWebElement?, RemoteWebDriver>("the page content") {
    driver.findElementByTagName("body") as RemoteWebElement?
}

val `the page title` = selector<String, RemoteWebDriver>("the page title") {
    driver.title
}



