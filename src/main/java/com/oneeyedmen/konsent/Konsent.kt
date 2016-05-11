package com.oneeyedmen.konsent

import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod
import java.lang.reflect.Method

class Konsent(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    protected override fun computeTestMethods(): List<FrameworkMethod> {
        return testClass.getAnnotatedMethods(Scenario::class.java)
            .sortedBy { it.getAnnotation(Scenario::class.java).index }
            .map { it.butNamed(it.getAnnotation(Scenario::class.java).name)}
    }

}

fun FrameworkMethod.butNamed(newName: String): FrameworkMethod  = RenamedFrameworkMethod(this.method, newName)

class RenamedFrameworkMethod(method: Method, private val newName: String) : FrameworkMethod(method) {
    override fun getName(): String = newName
}
