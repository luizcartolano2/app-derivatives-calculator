package model

import io.kotest.core.spec.style.BehaviorSpec

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
                    assert(price == spotValue)
                }
            }
            When("Interest rate is different than zero") {
                val interestRate = 0.005
                Then("Forward and spot are equal") {
                    val forwardPriceTest = Forward(tenor, interestRate)
                    val price = forwardPriceCalculator.calculate(spotValue, forwardPriceTest)
                    assert(price == spotValue)
                }
            }
        }
    }
}
