package com.oneeyedmen.konsent

import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.rules.TestRule
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod

class Konsent(
    klass: Class<*>,
    featureRuleFactory: (Class<*>) -> FeatureRule
) : BlockJUnit4ClassRunner(klass) {

    constructor(klass: Class<*>) : this(klass, ::usualFeatureRuleFor)

    private val featureRule = featureRuleFactory(klass)

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

@Suppress("UNUSED_PARAMETER")
fun usualFeatureRuleFor(klass: Class<*>) = FeatureRule(ApprovalsRule.usualRule())

