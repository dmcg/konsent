package com.oneeyedmen.konsent

import com.natpryce.hamkrest.Matcher


open class Actor<DriverT>(val name: String, val driver: DriverT, val recorder: FeatureRecorder) {

    companion object {
        fun <DriverT> with(name: String, driver: DriverT, recorder: FeatureRecorder) = Actor(name, driver, recorder)
    }

    operator fun invoke(function: Steps<DriverT>.() -> Unit) = anonymousSteps(this).function()

    fun <T> shouldSee(test: (Actor<DriverT>) -> T, matcher: Matcher<T>) = anonymousSteps(this).shouldSee(test, matcher)

    val he = anonymousSteps(this)
    val she = anonymousSteps(this)

}