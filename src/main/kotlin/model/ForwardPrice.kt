package model

import kotlin.math.exp

class ForwardPrice {
    fun calculate(spot: Double, forward: Forward): Double {
        return spot * exp(forward.interestRate * forward.tenor)
    }
}
