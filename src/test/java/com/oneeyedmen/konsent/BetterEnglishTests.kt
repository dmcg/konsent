package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import org.junit.runner.RunWith

@RunWith(Konsent::class)
class BetterEnglishTests : AcceptanceTest() {

    val driver = DummyDriver()
    val duncan = Actor.with("Duncan", driver, recorder)
    val fred = Actor.with("Fred", driver, recorder)

    @Scenario(1) fun `uses 'and' instead of repeating clause`() {
        Given(duncan).doesAThing("Duncan's thing")
        Given(fred).doesAThing("Fred's thing")
        Then(fred).shouldSee(theLastThingHappened, equalTo("Fred's thing happened"))
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Fred's thing happened"))
    }

    @Scenario(2) fun `uses 'and' instead of repeating name`() {
        Given(duncan).doesAThing("Duncan's thing")
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Fred's thing happened").not())
        Then(fred).shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
    }

}

val theLastThingHappened = selector<DummyDriver, String?>("the last thing happened") {
    driver.state
}

private fun Steps<DummyDriver>.doesAThing(s: String) = describedBy("""does a thing named "$s"""") {
    driver.doThing(s)
}

class DummyDriver {
    var state: String? = null

    fun doThing(s: String) {
        state = "$s happened"
    }

}
