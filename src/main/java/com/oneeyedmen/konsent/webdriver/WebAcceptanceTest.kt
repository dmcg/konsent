package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.AcceptanceTest
import com.oneeyedmen.konsent.Actor
import com.oneeyedmen.konsent.Preamble
import org.junit.AfterClass
import org.openqa.selenium.remote.RemoteWebDriver

open class WebAcceptanceTest(name: String? = null, preamble: Preamble? = null) : AcceptanceTest(name, preamble) {

    constructor(preamble: Preamble) : this(null, preamble)

    companion object {

        // subclasses should create driver in @BeforeClass
        lateinit var driver: RemoteWebDriver

        @AfterClass @JvmStatic fun shutdown() {
            driver.close()
        }
    }

    fun actorNamed(name: String) = Actor.with(name, driver, scenario.recorder)
}

