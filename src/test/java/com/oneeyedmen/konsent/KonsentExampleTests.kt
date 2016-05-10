package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import com.oneeyedmen.konsent.webdriver.*
import org.junit.Test


class KonsentExampleTests : ChromeAcceptanceTest() {

    val james = actorNamed("James")

    @Test fun `Example_dot_com loads`() {
        given(james).loadsThePageAt("http://example.com")
        then(james) {
            shouldSee(::`the page location`, pathContains("example.com"))
            shouldSee(::`the page title`, equalTo("Example Domain"))
            shouldSee(::`the page content`, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }
}