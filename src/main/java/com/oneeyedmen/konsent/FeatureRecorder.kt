package com.oneeyedmen.konsent


interface FeatureRecorder {
    fun featureStart(name: String, vararg preamble: String): Unit
    fun scenarioStart(name: String): Unit
    fun term(termName: String?, actor: Actor<*>, vararg items: Any): Unit
}

