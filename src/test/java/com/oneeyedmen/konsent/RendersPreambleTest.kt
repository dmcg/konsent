package com.oneeyedmen.konsent

import org.junit.runner.RunWith


@RunWith(Konsent::class)
@Preamble("As a developer named Duncan",
    "I want there to be a preamble in the output")
class RendersPreambleTest {

    @Scenario(index = 0) fun first() { }

}

