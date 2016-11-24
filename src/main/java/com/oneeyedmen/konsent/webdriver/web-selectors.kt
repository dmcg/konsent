package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.Actor
import com.oneeyedmen.konsent.selector
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import java.net.URI

fun <ResultT> webSelector(description: String, block: Actor<RemoteWebDriver>.() -> ResultT) = selector(description, block)


val thePageLocation = webSelector("the page location") {
    URI.create(driver.currentUrl)
}

val thePageContent = webSelector("the page content") {
    driver.findElementByTagName("body") as RemoteWebElement?
}

val thePageTitle = webSelector("the page title") {
    driver.title
}

@Deprecated("too cutesy", replaceWith = ReplaceWith("thePageLocation")) val `the page location` = thePageLocation
@Deprecated("too cutesy", replaceWith = ReplaceWith("thePageContent")) val `the page content` = thePageContent
@Deprecated("too cutesy", replaceWith = ReplaceWith("thePageTitle")) val `the page title` = thePageTitle



