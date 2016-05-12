package com.oneeyedmen.konsent.webdriver

import org.junit.BeforeClass
import org.openqa.selenium.chrome.ChromeDriver

open class ChromeAcceptanceTest : WebAcceptanceTest() {

    companion object {
        @BeforeClass @JvmStatic fun createDriver() {
            driver = ChromeDriver()
        }
    }
}