package util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class NormalDistributionTest : BehaviorSpec() {
    private val normalDistribution = NormalDistribution()

    init {
        Given("I calculate the distribution for positive values") {
            val value1 = normalDistribution.cumulativeDistribution(0.3900).roundTo(4)
            val value2 = normalDistribution.cumulativeDistribution(0.2486).roundTo(4)

            Then("Values are correct") {
                value1 shouldBe 0.6517
                value2 shouldBe 0.5982
            }
        }
        Given("I calculate the distribution for negative values") {
            val value1 = normalDistribution.cumulativeDistribution(-0.3900).roundTo(4)
            val value2 = normalDistribution.cumulativeDistribution(-0.2486).roundTo(4)

            Then("Values are correct") {
                value1 shouldBe 0.3483
                value2 shouldBe 0.4018
            }
        }
    }
}
