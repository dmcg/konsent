package com.oneeyedmen.konsent

import com.oneeyedmen.konsent.util.DelegateRunner
import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.ApproverFactory
import com.oneeyedmen.okeydoke.Sources
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.runner.RunWith
import java.io.File

// Shows that we can fall back to creating a custom test runner to override configuration
@RunWith(MySpecialRunner::class)
class FeatureRuleOverrideTests : AcceptanceTest() {

    @Scenario(0) fun first() {}

}

class MySpecialRunner(klass: Class<*>) : DelegateRunner(
    Konsent(klass) {
        singleApprovedDirFeatureRule(File("src/test/java"))
    })


private fun singleApprovedDirFeatureRule(dir: File) = FeatureRule(ApprovalsRule(singleDirApproverFactor(dir)))

private fun singleDirApproverFactor(sourceRoot: File) = ApproverFactory { testName, _ ->
    Approver(testName, Sources.`in`(sourceRoot))
}
