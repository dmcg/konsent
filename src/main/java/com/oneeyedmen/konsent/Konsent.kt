package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    private val featureRule = MyFeatureRule(ApprovalsRule.usualRule())

    override fun computeTestMethods(): List<FrameworkMethod> {
        return testClass.getAnnotatedMethods(Scenario::class.java)
            .map { it.butNamed(it.getAnnotation(Scenario::class.java).name)}
            .sortedBy { it.getAnnotation(Scenario::class.java).index }
    }

    override fun run(notifier: RunNotifier?) {
        super.run(notifier)
    }

    // we fake featureRule attached to class
    override fun classRules(): List<TestRule> {
        return super.classRules().plus(featureRule)
    }

    // and scenarioRule attached to instance
    override fun getTestRules(target: Any): List<TestRule> {
        val element: MyScenarioRule = MyScenarioRule(target, featureRule.recorder)
        return super.getTestRules(target).plus(element)
    }
}

private fun FrameworkMethod.butNamed(newName: String) = if (newName == "") this
    else object: FrameworkMethod(method) {
        override fun getName(): String = newName
    }

class MyFeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

    lateinit var recorder: FeatureRecorder

    override fun starting(description: Description) {
        approvalsRule.starting(description)
        recorder = TranscriptFeatureRecorder(approvalsRule.transcript())
        recorder.featureStart(nameFromClassName(description.testClass), *preambleFor(description.testClass))
        injectRecorder(description.testClass, null, recorder)
    }

    private fun preambleFor(testClass: Class<*>): Array<String> {
        val preamble = testClass.getAnnotation(Preamblex::class.java)
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

class MyScenarioRule(private val target: Any, private val recorder: FeatureRecorder) : TestWatcher() {
    override fun starting(description: Description) {
        injectRecorder(target.javaClass, target, recorder)
        recorder.scenarioStart(description.methodName)
    }
}

private fun <T> injectRecorder(clazz: Class<T>, target: T?, recorder: FeatureRecorder) {
    clazz.fields.filter { it.type == FeatureRecorder::class.java }.forEach { it.set(target, recorder) }
}
