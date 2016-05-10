package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.Preamble
import org.junit.BeforeClass
import org.openqa.selenium.chrome.ChromeDriver

open class ChromeAcceptanceTest(name: String?, preamble: Preamble? = null) : WebAcceptanceTest(name, preamble) {

    constructor(preamble: Preamble) : this(null, preamble)

    companion object {
        @BeforeClass @JvmStatic fun createDriver() {
            driver = ChromeDriver()
        }
    }
}