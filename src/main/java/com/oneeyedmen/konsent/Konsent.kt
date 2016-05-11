package com.oneeyedmen.konsent

import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    protected override fun computeTestMethods(): List<FrameworkMethod> {
        return testClass.getAnnotatedMethods(Scenario::class.java)
            .sortedBy { it.getAnnotation(Scenario::class.java).index }
    }

}