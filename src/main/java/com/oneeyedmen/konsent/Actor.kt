package com.oneeyedmen.konsent


open class Actor<DriverT>(val name: String, val pronoun:String, val driver: DriverT, val recorder: FeatureRecorder) {

    companion object {
        fun <DriverT> with(name: String, driver: DriverT, recorder: FeatureRecorder) = Actor.with(name, "(s)he", driver, recorder)
        fun <DriverT> with(name: String, pronoun: String, driver: DriverT, recorder: FeatureRecorder) = Actor(name, pronoun, driver, recorder)
    }

    operator fun invoke(function: Steps<DriverT>.() -> Unit) = anonymousSteps(this).function()

    val he = anonymousSteps(this)
    val she = anonymousSteps(this)

}