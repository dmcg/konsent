package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.okeydoke.TranscriptFeatureRecorder
import com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory
import com.oneeyedmen.okeydoke.Name
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod
import java.io.File

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    private val featureRule = FeatureRule(approvalsRule(File("src/test/java")))

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

private fun approvalsRule(sourceRoot: File): ApprovalsRule {
    return ApprovalsRule(
        fileSystemApproverFactory(sourceRoot)
    )
}

private class FeatureRule(private val approvalsRule: ApprovalsRule) : TestWatcher() {

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

private class ScenarioRule(private val target: Any, private val recorder: FeatureRecorder) : TestWatcher() {
    override fun starting(description: Description) {
        injectRecorder(target.javaClass, target, recorder)
        recorder.scenarioStart(description.methodName)
    }
}

private fun <T> injectRecorder(clazz: Class<T>, target: T?, recorder: FeatureRecorder) {
    clazz.fields.filter { it.type == FeatureRecorder::class.java }.forEach { it.set(target, recorder) }
}

private val spaceOutCamelCase = Regex("(\\p{Ll})(\\p{Lu})")
private fun String.`space Out Camel Case`() = this.replace(spaceOutCamelCase, "$1 $2")

private fun featureNameFrom(description: Description) =
    nameFromClassAnnotation(description) ?: description.testClass.simpleName.`space Out Camel Case`()

private fun nameFromClassAnnotation(description: Description): String? =
    description.testClass?.getAnnotation(Name::class.java)?.value
