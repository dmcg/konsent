package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    private val featureRule = FeatureRule(ApprovalsRule.usualRule())

    override fun computeTestMethods() = testClass.getAnnotatedMethods(Scenario::class.java)
        .map { it.butNamed(it.getAnnotation(Scenario::class.java).name)}
        .sortedBy { it.getAnnotation(Scenario::class.java).index }

    // we fake a FeatureRule attached to the class
    override fun classRules(): List<TestRule> = super.classRules().apply {
        add(featureRule)
    }

    // and a ScenarioRule attached to the instance
    override fun getTestRules(target: Any): List<TestRule> = super.getTestRules(target).apply {
        add(ScenarioRule(target, featureRule.recorder))
    }

    private fun FrameworkMethod.butNamed(newName: String) = if (newName == "") this
        else object: FrameworkMethod(method) {
            override fun getName(): String = newName
        }
}

private class FeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

    lateinit var recorder: FeatureRecorder

    override fun starting(description: Description) {
        approvalsRule.starting(description)
        recorder = TranscriptFeatureRecorder(approvalsRule.transcript())
        recorder.featureStart(nameFromClassName(description.testClass), *preambleFor(description.testClass))
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

    private val spaceOutCamelCase = Regex("(\\p{Ll})(\\p{Lu})")
    private fun String.`space Out Camel Case`() = this.replace(spaceOutCamelCase, "$1 $2")
    private fun nameFromClassName(clazz: Class<*>) = clazz.simpleName.`space Out Camel Case`()
}

private class ScenarioRule(private val target: Any, private val recorder: FeatureRecorder) : TestWatcher() {
    override fun starting(description: Description) {
        injectRecorder(target.javaClass, target, recorder)
        recorder.scenarioStart(description.methodName)
    }
}

private fun <T> injectRecorder(clazz: Class<T>, target: T?, recorder: FeatureRecorder) {
    clazz.fields.filter { it.type == FeatureRecorder::class.java }.forEach { it.set(target, recorder) }
}
