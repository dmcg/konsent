package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod
import java.lang.reflect.Method

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    private val featureRule = MyFeatureRule(ApprovalsRule.usualRule())

    protected override fun computeTestMethods(): List<FrameworkMethod> {
        return testClass.getAnnotatedMethods(Scenario::class.java)
            .map { it.butNamed(it.getAnnotation(Scenario::class.java).name)}
            .sortedBy { it.getAnnotation(Scenario::class.java).index }
    }

    override fun run(notifier: RunNotifier?) {
        super.run(notifier)
    }

    override fun classRules(): List<TestRule> {
        return super.classRules().plus(featureRule)
    }

    override fun getTestRules(target: Any): List<TestRule> {
        return super.getTestRules(target).plus(MyScenarioRule(featureRule.recorder))
    }
}

private fun FrameworkMethod.butNamed(newName: String): FrameworkMethod = if (newName == "") this else RenamedFrameworkMethod(this.method, newName)

private class RenamedFrameworkMethod(method: Method, private val newName: String) : FrameworkMethod(method) {
    override fun getName(): String = newName
}

class MyFeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

    lateinit var recorder: FeatureRecorder

    override fun starting(description: Description) {
        approvalsRule.starting(description)
        recorder = TranscriptFeatureRecorder(approvalsRule.transcript())
        recorder.featureStart(nameFromClassName(description.testClass), *preambleFor(description.testClass))
    }

    private fun preambleFor(testClass: Class<*>): Array<String> {
        val preamble = testClass.getAnnotation(Preamblex::class.java)
        return listOf(preamble.p1, preamble.p2, preamble.p3).filter { it.isNotBlank() }.toTypedArray()
    }

    override fun succeeded(description: Description) {
        approvalsRule.succeeded(description)
    }

}

class MyScenarioRule(val recorder: FeatureRecorder) : TestWatcher() {
    override fun starting(description: Description) {
        recorder.scenarioStart(description.methodName)
    }
}

private val spaceOutCamelCase = Regex("(\\p{Ll})(\\p{Lu})")
private fun String.`space Out Camel Case`() = this.replace(spaceOutCamelCase, "$1 $2")
private fun nameFromClassName(instance: Any) = instance.javaClass.simpleName.`space Out Camel Case`()
