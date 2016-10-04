package com.oneeyedmen.konsent.webdriver

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.oneeyedmen.konsent.matcherOf
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement
import java.net.URI

fun <T: WebElement?> elementMatcher(description: String, predicate: (T) -> Boolean) = object: Matcher.Primitive<T>()  {
    override val description = description
    override fun invoke(actual: T): MatchResult = if (predicate(actual)) MatchResult.Match
    else MatchResult.Mismatch("was ${describeWebElement(actual)}")

    private fun describeWebElement(actual: T): String {
        if (actual == null)
            return "[No such element]"
        else
            return "<${actual.tagName}>${actual.text}</>"
    }
}

fun containsALink(text: String, href: String) = elementMatcher<RemoteWebElement?>("contains a link [$text]($href)") { element ->
    element != null && element.findElementsByXPath("//a[@href='$href'][text()='$text']").isNotEmpty()
}

val isntThere = elementMatcher<RemoteWebElement?>("isn't there") { it == null }

val hasSomeContent = elementMatcher<RemoteWebElement?>("has some content") {
    it != null && it.isDisplayed
}

fun pathContains(pathElement: String) = matcherOf<URI>(""""location contains "$pathElement"""") { uri ->
    uri.toString().contains(pathElement)
}

