package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import com.oneeyedmen.konsent.webdriver.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.net.URI

//README_TEXT
@FixMethodOrder(MethodSorters.JVM)
class KonsentExampleTests : ChromeAcceptanceTest(preamble(
    "As a developer named Duncan",
    "I want to know that example.com is up and running")) {

    val duncan = actorNamed("Duncan")

    @Test fun `Example_dot_com loads`() {
        Given(duncan).loadsThePageAt("http://example.com")
        Then(duncan) {
            shouldSee(::`the page location`, pathContains("example.com"))
            shouldSee(::`the page title`, equalTo("Example Domain"))
            shouldSee(::`the page content`, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }

    @Test fun `Following a link from example_dot_com`() {
        Given(duncan).loadsThePageAt("http://example.com")
        When(duncan).followsTheLink("More information...", "http://www.iana.org/domains/example")
        Then(duncan).shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

    @Test fun `Dispensing with the given when then`() {
        duncan.he.loadsThePageAt("http://example.com")
        duncan.he.followsTheLink("More information...", "http://www.iana.org/domains/example")
        duncan.shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }
}
//README_TEXT