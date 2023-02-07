package model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ForwardPriceTest : BehaviorSpec() {
    private val forwardPriceCalculator = ForwardPrice()
    private val spotValue = 100.0

    init {
        Given("I check the forward price with zero tenor") {
            val tenor = 0.0
            When("Interest rate is zero") {
                val interestRate = 0.0
                Then("Forward and spot are equal") {
                    val forwardPriceTest = Forward(tenor, interestRate)
                    val price = forwardPriceCalculator.calculate(spotValue, forwardPriceTest)
                    price shouldBe spotValue
                }
            }
            When("Interest rate is different than zero") {
                val interestRate = 0.05
                Then("Forward and spot are equal") {
                    val forwardPriceTest = Forward(tenor, interestRate)
                    val price = forwardPriceCalculator.calculate(spotValue, forwardPriceTest)
                    price shouldBe spotValue
                }
            }
        }
        Given("I check the forward price with non zero tenor") {
            val tenor = 1.0
            When("Interest rate is zero") {
                val interestRate = 0.0
                Then("Forward and spot are equal") {
                    val forwardPriceTest = Forward(tenor, interestRate)
                    val price = forwardPriceCalculator.calculate(spotValue, forwardPriceTest)
                    price shouldBe spotValue
                }
            }
            When("Interest rate is different than zero") {
                val interestRate = 0.05
                Then("Forward and spot are not equal") {
                    val forwardPriceTest = Forward(tenor, interestRate)
                    val price = forwardPriceCalculator.calculate(spotValue, forwardPriceTest)
                    price shouldBe 105.13
                }
            }
        }
    }
}
