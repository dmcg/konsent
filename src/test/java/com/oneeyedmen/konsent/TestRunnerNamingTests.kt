package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
class TestRunnerNamingTests : AcceptanceTest() {

    @Scenario(0, "1") fun first() {}

    @Scenario(1, "number 2") fun second() {}

    @Scenario(2, "erm, 3") fun third() {}

    @Scenario(3, "the fouth") fun aaa() {}

}

