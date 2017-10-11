package com.oneeyedmen.konsent

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ScenarioRule(
    private val target: Any,
    private val recorder: FeatureRecorder
) : TestWatcher() {

    override fun starting(description: Description) {
        injectRecorder(target.javaClass, target, recorder)
        recorder.scenarioStart(description.methodName)
    }
}