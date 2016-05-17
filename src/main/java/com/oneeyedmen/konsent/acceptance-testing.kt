package com.oneeyedmen.konsent

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher

fun <DriverT> Given(actor: Actor<DriverT>, function: Steps<DriverT>.() -> Unit) = function.invoke(givenSteps(actor))
fun <DriverT> Given(actor: Actor<DriverT>) = givenSteps(actor)

fun <DriverT> When(actor: Actor<DriverT>, function: Steps<DriverT>.() -> Unit) = function.invoke(whenSteps(actor))
fun <DriverT> When(actor: Actor<DriverT>) = whenSteps(actor)

fun <DriverT> Then(actor: Actor<DriverT>, function: Steps<DriverT>.() -> Unit) = function.invoke(thenSteps(actor))
fun <DriverT> Then(actor: Actor<DriverT>) = thenSteps(actor)

fun <DriverT, MatcherT> Steps<DriverT>.shouldSee(selector: Selector<DriverT, MatcherT>, matcher: Matcher<MatcherT>) {
    val matchResult = matcher.invoke(selector.select(actor))
    val resultString = when(matchResult) {
        is MatchResult.Mismatch -> "[expected ${matcher.description}, ${matchResult.description}]"
        else -> matcher.description
    }
    record("sees", selector.description, resultString)
}

class Steps<DriverT>(val actor: Actor<DriverT>, val driver: DriverT, val recorder: FeatureRecorder, private val term: String?) {
    fun describedBy(stepName: String, block: Steps<DriverT>.() -> Unit) {
        record(stepName)
        block()
    }
    fun record(vararg args: Any) = recorder.term(term, actor.name, *args)

}

fun <DriverT> anonymousSteps(actor: Actor<DriverT>) = Steps(actor, actor.driver, actor.recorder, null)
fun <DriverT> givenSteps(actor: Actor<DriverT>) = Steps(actor, actor.driver, actor.recorder, "Given")
fun <DriverT> whenSteps(actor: Actor<DriverT>) = Steps(actor, actor.driver, actor.recorder, "When")
fun <DriverT> thenSteps(actor: Actor<DriverT>) = Steps(actor, actor.driver, actor.recorder, "Then")

