package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
class TestRunnerNamingTests : AcceptanceTest() {

    @Scenario(name="1", index = 0) fun first() {}

    @Scenario(name="number 2", index = 1) fun second() {}

    @Scenario(name="erm, 3", index = 2) fun third() {}

    @Scenario(name="the fouth", index = 3) fun aaa() {}

}

