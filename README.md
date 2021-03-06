
Konsent
=========

An acceptance testing library for Kotlin.

[KonsentExampleTests](src/test/java/com/oneeyedmen/konsent/KonsentExampleTests.kt)
shows how to write a test.

```kotlin
@RunWith(Konsent::class)
@Preamble(
    "As a developer named Duncan",
    "I want to know that example.com is up and running")
class KonsentExampleTests : ChromeAcceptanceTest() {

    val duncan = actorNamed("Duncan")

    @Scenario(1) fun `Example_dot_com loads`() {
        Given(duncan).loadsThePageAt("http://example.com")
        Then(duncan) {
            shouldSee(thePageLocation, pathContains("example.com"))
            shouldSee(thePageTitle, equalTo("Example Domain"))
            shouldSee(thePageContent, containsALink("More information...", "http://www.iana.org/domains/example"))
        }
    }

    @Scenario(2, "Following a link from example.com") fun cant_have_dots_in_quoted_method_names() {
        Given(duncan).loadsThePageAt("http://example.com")
        When(duncan).followsTheLink("More information...", "http://www.iana.org/domains/example")
        Then(duncan).shouldSee(thePageLocation, equalTo(URI("http://www.iana.org/domains/reserved")))
    }

    @Scenario(3) fun `Dispensing with the given when then`() {
        duncan.he.loadsThePageAt("http://example.com")
        duncan.he.followsTheLink("More information...", "http://www.iana.org/domains/example")
        duncan.he.shouldSee(thePageLocation, equalTo(URI("http://www.iana.org/domains/reserved")))
    }
}
```

This writes an approved file

```gherkin

Feature: Konsent Example Tests
    As a developer named Duncan
    I want to know that example.com is up and running

    Scenario: Example_dot_com loads
        Given Duncan loads the page at "http://example.com"
        Then he sees the page location "location contains "example.com"
        and the page title is equal to "Example Domain"
        and the page content contains a link [More information...](http://www.iana.org/domains/example)

    Scenario: Following a link from example.com
        Given Duncan loads the page at "http://example.com"
        When he follows the link [More information...](http://www.iana.org/domains/example)
        Then he sees the page location is equal to http://www.iana.org/domains/reserved

    Scenario: Dispensing with the given when then
        Duncan loads the page at "http://example.com"
        he follows the link [More information...](http://www.iana.org/domains/example)
        he sees the page location is equal to http://www.iana.org/domains/reserved

```

Konsent is available at Maven central.

