package util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class RoundNumberTest : BehaviorSpec() {
    init {
        Given("I do some rounding tests") {
            When("I round the same number to different amount of decimals") {
                Then("Values are right") {
                    21.0538.roundTo(0) shouldBe 21
                    21.0538.roundTo(1) shouldBe 21.1
                    21.0538.roundTo(2) shouldBe 21.05
                    21.0538.roundTo(3) shouldBe 21.054
                }
            }
            When("I round numbers of different signs") {
                Then("Values are right") {
                    23.5.roundTo(0) shouldBe 24
                    (-23.5).roundTo(0) shouldBe -23
                }
            }
        }
    }
}
