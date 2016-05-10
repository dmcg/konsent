package com.oneeyedmen.konsent

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.describe

fun <T> matcherOf(description: String, predicate: (T) -> Boolean) = object: Matcher.Primitive<T>()  {
    override fun description() = description
    override fun invoke(actual: T): MatchResult = if (predicate(actual)) MatchResult.Match
    else MatchResult.Mismatch("was ${describe(actual)}")
}
