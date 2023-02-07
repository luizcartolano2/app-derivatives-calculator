package model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import util.NormalDistribution
import util.roundTo
import kotlin.math.exp

class EuroVanillaPriceTest : BehaviorSpec() {
    private val priceCalculator = EuroVanillaPrice()
    private val spotValue = 60.0

    init {
        Given("I check the EuroVanilla price calculator") {
            val option = EuroVanilla(
                strike = 58.0,
                tenor = 0.5,
                sigma = 0.2,
                interestRate = 0.035,
                foreignRate = 0.0125
            )

            When("I check the first derivative method") {
                Then("The value is calculated correctly") {
                    val price = priceCalculator.blackFirstDerivative(spotValue, option)
                    price.roundTo(2) shouldBe 0.39
                }
            }
            When("I check the second derivative method") {
                val price = priceCalculator.blackSecondDerivative(spotValue, option)
                price.roundTo(2) shouldBe 0.25
            }
            When("I check the normal of the derivatives") {
                val normalDistribution = NormalDistribution()
                Then("The normal of first derivative is correct") {
                    val firstDerivation = priceCalculator.blackFirstDerivative(spotValue, option)
                    val value = normalDistribution.cumulativeDistribution(firstDerivation)

                    value.roundTo(2) shouldBe 0.65
                }
                Then("The normal of second derivative is correct") {
                    val secondDerivation = priceCalculator.blackSecondDerivative(spotValue, option)
                    val value = normalDistribution.cumulativeDistribution(secondDerivation)

                    value.roundTo(2) shouldBe 0.60
                }
                Then("The normal of the inverse of first derivative is correct") {
                    val firstDerivation = priceCalculator.blackFirstDerivative(spotValue, option)
                    val value = normalDistribution.cumulativeDistribution(-firstDerivation)

                    value.roundTo(2) shouldBe 0.35
                }
                Then("The normal of the inverse of second derivative is correct") {
                    val secondDerivation = priceCalculator.blackSecondDerivative(spotValue, option)
                    val value = normalDistribution.cumulativeDistribution(-secondDerivation)

                    value.roundTo(2) shouldBe 0.40
                }
            }
            When("I calculate the call price") {
                val isCall = true
                Then("The price is calculated correctly") {
                    val price = priceCalculator.calculate(spotValue, option, isCall)
                    price.roundTo(2) shouldBe 4.77
                }
            }
            When("I calculate the put price") {
                val isCall = false
                Then("The price is calculated correctly") {
                    val price = priceCalculator.calculate(spotValue, option, isCall)
                    price.roundTo(2) shouldBe 2.14
                }
            }
            When("I check the call - put parity") {
                Then("The parity is respected") {
                    val call = priceCalculator.calculate(spotValue, option, isCall = true)
                    val put = priceCalculator.calculate(spotValue, option, isCall = false)

                    val firstSide = call + option.strike * exp(-option.interestRate * option.tenor)
                    val secondSide = put + spotValue * exp(-option.foreignRate * option.tenor)

                    firstSide.roundTo(2) shouldBe 61.76
                    secondSide.roundTo(2) shouldBe 61.77
                }
            }
        }
    }
}
