package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.Name
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class FeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

    lateinit var recorder: FeatureRecorder

    override fun starting(description: Description) {
        approvalsRule.starting(description)
        recorder = TranscriptFeatureRecorder(approvalsRule.transcript())
        recorder.featureStart(featureNameFrom(description), *preambleFor(description.testClass))
        injectRecorder(description.testClass, null, recorder)
    }

    private fun preambleFor(testClass: Class<*>): Array<String> {
        val preamble = testClass.getAnnotation(Preamble::class.java)
        return if (preamble != null) listOf(preamble.p1, preamble.p2, preamble.p3).filter { it.isNotBlank() }.toTypedArray()
        else emptyArray()
    }

    override fun succeeded(description: Description) {
        approvalsRule.succeeded(description)
    }

}

internal fun <T> injectRecorder(clazz: Class<T>, target: T?, recorder: FeatureRecorder) {
    clazz.fields.filter { it.type == FeatureRecorder::class.java }.forEach { it.set(target, recorder) }
}

private val spaceOutCamelCase = Regex("(\\p{Ll})(\\p{Lu})")
private fun String.`space Out Camel Case`() = this.replace(spaceOutCamelCase, "$1 $2")
private fun featureNameFrom(description: Description) =
    nameFromClassAnnotation(description) ?: description.testClass.simpleName.`space Out Camel Case`()

private fun nameFromClassAnnotation(description: Description): String? =
    description.testClass?.getAnnotation(Name::class.java)?.value