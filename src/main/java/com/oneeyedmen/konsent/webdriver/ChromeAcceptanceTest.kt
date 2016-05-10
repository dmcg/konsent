package com.oneeyedmen.konsent.webdriver

import org.junit.BeforeClass
import org.openqa.selenium.chrome.ChromeDriver

open class ChromeAcceptanceTest(name: String? = null, vararg preamble: String)
: WebAcceptanceTest(name, *preamble) {

    companion object {
        @BeforeClass @JvmStatic fun createDriver() {
            driver = ChromeDriver()
        }
    }
}