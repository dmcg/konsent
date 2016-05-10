package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Class (static) TestRule that writes the Feature-level info.
 */
class FeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

    lateinit var recorder: FeatureRecorder
    private var featureStarted = false

    override fun starting(description: Description) {
        featureStarted = false // reset as we're reused by all AcceptanceTests
        approvalsRule.starting(description)
        recorder = TranscriptFeatureRecorder(approvalsRule.transcript())
    }

    override fun succeeded(description: Description) {
        approvalsRule.succeeded(description)
    }

    fun scenarioRule(test: AcceptanceTest) =
        if (featureStarted) {
            ScenarioRule(recorder)
        } else {
            featureStarted = true
            FirstScenarioRule(test, recorder)
        }
}

open class ScenarioRule(val recorder: FeatureRecorder) : TestWatcher() {
    override fun starting(description: Description) {
        recorder.scenarioStart(description.methodName)
    }
}

// The complication here is that the test name etc are not available until we have an instance of AcceptanceTest. So
// the FeatureRule, which is static, does not know the things it needs to tell the recorder. Instead it lets this rule,
// which is an instance rule, tell the recorder before recording the first scenario.
class FirstScenarioRule(private val test: AcceptanceTest, recorder: FeatureRecorder) : ScenarioRule(recorder) {
    override fun starting(description: Description) {
        recordFeatureStart()
        super.starting(description)
    }

    private fun recordFeatureStart() {
        recorder.featureStart(
            test.name ?: nameFromClassName(test),
            *(test.preamble?.strings ?: emptyArray()))
    }
}

private val spaceOutCamelCase = Regex("(\\p{Ll})(\\p{Lu})")
private fun String.`space Out Camel Case`() = this.replace(spaceOutCamelCase, "$1 $2")
private fun nameFromClassName(instance: Any) = instance.javaClass.simpleName.`space Out Camel Case`()
