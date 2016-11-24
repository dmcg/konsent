package com.oneeyedmen.konsent

import com.natpryce.hamkrest.equalTo
import org.junit.runner.RunWith

@RunWith(Konsent::class)
class BetterEnglishTests : AcceptanceTest() {

    val driver = DummyDriver()
    val duncan = Actor.with("Duncan", "he", driver, recorder)
    val alice = Actor.with("Alice", "she", driver, recorder)

    @Scenario(1) fun `uses 'and' instead of repeating clause`() {
        Given(duncan).doesAThing("Duncan's thing")
        Given(alice).doesAThing("Alice's thing")
        Then(alice).shouldSee(theLastThingHappened, equalTo("Alice's thing happened"))
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Alice's thing happened"))
    }

    @Scenario(2) fun `uses 'and' instead of repeating name and operation`() {
        Given(duncan).doesAThing("Duncan's thing")
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Alice's thing happened").not())
        Then(alice).shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
    }

    @Scenario(3) fun `uses 'he' instead of repeating name`() {
        Then(duncan).doesAThing("Duncan's thing")
        Then(duncan).shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
    }

    @Scenario(4) fun `uses 'he' instead of repeating name with anonymous term`() {
        duncan.he.doesAThing("Duncan's thing")
        duncan.he.shouldSee(theLastThingHappened, equalTo("Duncan's thing happened"))
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
