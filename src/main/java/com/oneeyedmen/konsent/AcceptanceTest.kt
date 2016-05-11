package com.oneeyedmen.konsent

import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.junit.ClassRule
import org.junit.Rule

open class AcceptanceTest(val name: String? = null, val preamble: Preamble?)
{
    constructor(preamble: Preamble? = null) : this(null, preamble)

    companion object {
        @ClassRule @JvmField val feature = FeatureRule(ApprovalsRule.usualRule())
    }

    @Rule @JvmField val scenario = feature.scenarioRule(this)
}