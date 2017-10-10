package com.oneeyedmen.konsent

import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith


@RunWith(Konsent::class)
class InjectsRecorderTest {

    companion object {
        lateinit var recorder: FeatureRecorder
    }

    private val recorderDuringConstruction  = recorder

    @Scenario(index = 0) fun first() {
        assertNotNull(recorder)
        assertNotNull(recorderDuringConstruction)
    }
}

