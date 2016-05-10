package com.oneeyedmen.konsent.webdriver

import com.oneeyedmen.konsent.AcceptanceTest
import com.oneeyedmen.konsent.Actor
import org.junit.AfterClass
import org.openqa.selenium.remote.RemoteWebDriver

open class WebAcceptanceTest(name: String? = null, vararg preamble: String)
: AcceptanceTest(name, preamble as Array<String>) {

    companion object {

        // subclasses should create driver in @BeforeClass
        lateinit var driver: RemoteWebDriver

        @AfterClass @JvmStatic fun shutdown() {
            driver.close()
        }
    }

    fun actorNamed(name: String) = Actor.with(name, driver, scenario.recorder)
}

