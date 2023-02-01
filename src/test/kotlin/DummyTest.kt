import io.kotest.core.spec.style.BehaviorSpec

class DummyTest : BehaviorSpec() {
    init {
        Given("I do a dummy test") {
            When("Success") {
                assert(true)
            }
        }
    }
}
