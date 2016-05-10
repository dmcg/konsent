package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import com.oneeyedmen.konsent.webdriver.*
import org.junit.Test
import java.net.URI


class KonsentExampleTests : ChromeAcceptanceTest(null,
    "As a developer named Duncan",
    "I want to know that example.com is up and running") {

    val duncan = actorNamed("Duncan")

    @Test fun `Example_dot_com loads`() {
        given(duncan).loadsThePageAt("http://example.com")
        then(duncan) {
            shouldSee(::`the page location`, pathContains("example.com"))
            shouldSee(::`the page title`, equalTo("Example Domain"))
            shouldSee(::`the page content`, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }

    @Test fun `Following a link from example_dot_com`() {
        given(duncan).loadsThePageAt("http://example.com")
        wheN(duncan).followsTheLink("More information...", "http://www.iana.org/domains/example")
        then(duncan).shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

    @Test fun `Dispensing with the given when then`() {
        duncan.he.loadsThePageAt("http://example.com")
        duncan.he.followsTheLink("More information...", "http://www.iana.org/domains/example")
        duncan.shouldSee(::`the page location`, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

}