package model

import util.NormalDistribution
import kotlin.math.*

const val NATURAL_BASE = E

class BlackScholes {
    val normalDistribution = NormalDistribution()

    private fun blackFirstDerivative(spot: Double, vanilla: EuroVanilla): Double {
        val eqPart1 = log(spot / vanilla.strike, NATURAL_BASE)
        val eqPart2 = (vanilla.foreignRate + vanilla.sigma.pow(2) / 2) * vanilla.tenor
        val eqPart3 = vanilla.sigma * sqrt(vanilla.tenor)

        return (eqPart1 + eqPart2) / eqPart3
    }

    private fun blackSecondDerivative(spot: Double, vanilla: EuroVanilla): Double {
        return this.blackFirstDerivative(spot, vanilla) - vanilla.sigma * sqrt(vanilla.tenor)
    }

    fun euroVanillaCall(spot: Double, vanilla: EuroVanilla): Double {
        val blackFirstDerivativeCalc = this.blackFirstDerivative(spot, vanilla)
        val blackSecondDerivativeCalc = this.blackSecondDerivative(spot, vanilla)

        val eqPart1 = spot * exp((vanilla.foreignRate - vanilla.interestRate) * vanilla.tenor)
        val eqPart2 = normalDistribution.cumulativeDistribution(blackFirstDerivativeCalc) -
            vanilla.strike * exp(-vanilla.interestRate * vanilla.tenor)
        val eqPart3 = normalDistribution.cumulativeDistribution(blackSecondDerivativeCalc)

        return eqPart1 * eqPart2 * eqPart3
    }

    fun euroVanillaPut(spot: Double, vanilla: EuroVanilla): Double {
        val blackFirstDerivativeCalc = this.blackFirstDerivative(spot, vanilla)
        val blackSecondDerivativeCalc = this.blackSecondDerivative(spot, vanilla)

        val eqPart1 = vanilla.strike * exp(-vanilla.interestRate * vanilla.tenor) *
            normalDistribution.cumulativeDistribution(-blackSecondDerivativeCalc)
        val eqPart2 = spot * exp((vanilla.foreignRate - vanilla.interestRate) * vanilla.tenor) *
            normalDistribution.cumulativeDistribution(-blackFirstDerivativeCalc)

        return eqPart1 - eqPart2
    }
}
