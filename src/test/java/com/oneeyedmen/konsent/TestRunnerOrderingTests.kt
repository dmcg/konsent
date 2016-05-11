package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
class TestRunnerOrderingTests : AcceptanceTest() {

    @Scenario(name="", index = 1) fun second() {
    }

    @Scenario(name="", index = 0) fun first() {
    }

    @Scenario(name="", index = 2) fun third() {
    }

    @Scenario(name="", index = 4) fun aaa() {
    }

}

