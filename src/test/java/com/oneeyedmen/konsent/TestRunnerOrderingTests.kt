package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
class TestRunnerOrderingTests : AcceptanceTest() {

    @Scenario(1) fun second() { }

    @Scenario(0) fun first() { }

    @Scenario(2) fun third() { }

    @Scenario(4) fun aaa() { }

}

