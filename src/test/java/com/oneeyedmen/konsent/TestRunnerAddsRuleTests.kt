package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
class TestRunnerAddsRuleTests {

    @Scenario(index = 1) fun second() { }

    @Scenario(index = 0) fun first() { }

    @Scenario(index = 2) fun third() { }

    @Scenario(index = 4) fun aaa() { }

}
