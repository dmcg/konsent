package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import com.oneeyedmen.konsent.webdriver.*
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI

@RunWith(Konsent::class)

@Preamblex(
    "As a developer named Duncan",
    "I want to know that example.com is up and running")
class KonsentTest : Parent() {

    val duncan = actorNamed("Duncan")

    @Scenario(index = 0) fun `Example_dot_com loads`() {
        Given(duncan).loadsThePageAt("http://example.com")
        Then(duncan) {
            shouldSee(::`the page location`, pathContains("example.com"))
            shouldSee(::`the page title`, equalTo("Example Domain"))
            shouldSee(::`the page content`, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }

    @Scenario(index = 1) fun `Following a link from example_dot_com`() {
        Given(duncan).loadsThePageAt("http://example.com")
        When(duncan).followsTheLink("More information...", "http://www.iana.org/domains/example")
        Then(duncan).shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

    @Scenario(index = 2) fun `Dispensing with the given when then`() {
        duncan.he.loadsThePageAt("http://example.com")
        duncan.he.followsTheLink("More information...", "http://www.iana.org/domains/example")
        duncan.shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }


}

@RunWith(Konsent::class)
open class Parent {

    fun actorNamed(name: String) = Actor.with(name, driver, recorder)

    companion object {
        lateinit var recorder: FeatureRecorder

        // subclasses should create driver in @BeforeClass
        lateinit var driver: RemoteWebDriver

        @BeforeClass @JvmStatic fun createDriver() {
            driver = ChromeDriver()
        }

        @AfterClass @JvmStatic fun shutdown() {
            driver.close()
        }
    }
}
