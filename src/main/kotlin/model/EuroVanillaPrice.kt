package model

import util.NormalDistribution
import util.roundTo
import kotlin.math.E
import kotlin.math.exp
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

const val NATURAL_BASE = E

class EuroVanillaPrice {
    private val normalDistribution = NormalDistribution()

    internal fun blackFirstDerivative(spot: Double, vanilla: EuroVanilla): Double {
        val eqPart1 = log(spot / vanilla.strike, NATURAL_BASE)
        val eqPart2 = ((vanilla.interestRate - vanilla.foreignRate) + vanilla.sigma.pow(2) / 2) * vanilla.tenor
        val eqPart3 = vanilla.sigma * sqrt(vanilla.tenor)

        return (eqPart1 + eqPart2) / eqPart3
    }

    internal fun blackSecondDerivative(spot: Double, vanilla: EuroVanilla): Double {
        return this.blackFirstDerivative(spot, vanilla) - vanilla.sigma * sqrt(vanilla.tenor)
    }

    private fun euroVanillaCall(spot: Double, vanilla: EuroVanilla): Double {
        val blackFirstDerivativeCalc = this.blackFirstDerivative(spot, vanilla)
        val blackSecondDerivativeCalc = this.blackSecondDerivative(spot, vanilla)

        // S0 * e^(-qT) * N(d1)
        val eqPart1 = spot * exp(-vanilla.foreignRate * vanilla.tenor) *
                normalDistribution.cumulativeDistribution(blackFirstDerivativeCalc)
        // K * e^(-rT) * N(d2)
        val eqPart2 = vanilla.strike * exp(-vanilla.interestRate * vanilla.tenor) *
                normalDistribution.cumulativeDistribution(blackSecondDerivativeCalc)

        // C = S0 * e^(-qT) * N(d1) - K * e^(-rT) * N(d2)
        return eqPart1 - eqPart2
    }

    private fun euroVanillaPut(spot: Double, vanilla: EuroVanilla): Double {
        val blackFirstDerivativeCalc = this.blackFirstDerivative(spot, vanilla)
        val blackSecondDerivativeCalc = this.blackSecondDerivative(spot, vanilla)

        // K * e^(-rT) * N(-d2)
        val eqPart1 = vanilla.strike * exp(-vanilla.interestRate * vanilla.tenor) *
            normalDistribution.cumulativeDistribution(-blackSecondDerivativeCalc)
        // S0 * e^(-qT) * N(-d1)
        val eqPart2 = spot * exp(-vanilla.foreignRate * vanilla.tenor) *
            normalDistribution.cumulativeDistribution(-blackFirstDerivativeCalc)

        // P = K * e^(-rT) * N(-d2) - S0 * e^(-qT) * N(-d1)
        return eqPart1 - eqPart2
    }

    fun calculate(spot: Double, vanilla: EuroVanilla, isCall: Boolean, decimalNumbers: Int = 2): Double {
        val price = if (isCall) {
            euroVanillaCall(spot, vanilla)
        } else {
            euroVanillaPut(spot, vanilla)
        }

        return price.roundTo(decimalNumbers)
    }
}
