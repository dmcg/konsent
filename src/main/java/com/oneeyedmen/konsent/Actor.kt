package com.oneeyedmen.konsent


open class Actor<DriverT>(val name: String, val driver: DriverT, val recorder: FeatureRecorder) {

    companion object {
        fun <DriverT> with(name: String, driver: DriverT, recorder: FeatureRecorder) = Actor(name, driver, recorder)
    }

    operator fun invoke(function: Steps<DriverT>.() -> Unit) = anonymousSteps(this).function()

    val he = anonymousSteps(this)
    val she = anonymousSteps(this)

}