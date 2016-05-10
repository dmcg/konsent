package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.Steps
import org.openqa.selenium.remote.RemoteWebDriver

fun Steps<RemoteWebDriver>.loadsThePageAt(url: String) = describedBy("""loads the page at "$url"""") {
    driver.get(url)
}

fun Steps<RemoteWebDriver>.followsTheLink(text: String, href: String) = describedBy(""""follows the link [$text]($href)""") {
    driver.findElementByXPath("//a[@href='$href'][text()='$text']").click()
}

