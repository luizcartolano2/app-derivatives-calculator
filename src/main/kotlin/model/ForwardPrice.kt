package model

import util.roundTo
import kotlin.math.exp

class ForwardPrice {
    fun calculate(spot: Double, forward: Forward, decimalNumbers: Int = 2): Double {
        return (spot * exp(forward.interestRate * forward.tenor)).roundTo(decimalNumbers)
    }
}
