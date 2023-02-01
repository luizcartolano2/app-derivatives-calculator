package util

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

const val P = 0.2316419
const val B1 = 0.319381530
const val B2 = -0.356563782
const val B3 = 1.781477937
const val B4 = -1.821255978
const val B5 = 1.330274429

@Suppress("MagicNumber")
class NormalDistribution {
    fun cumulativeDistribution(value: Double): Double {
        val t: Double = 1 / (1 + P * abs(value))
        val t1: Double = B1 * t.pow(1.0)
        val t2: Double = B2 * t.pow(2.0)
        val t3: Double = B3 * t.pow(3.0)
        val t4: Double = B4 * t.pow(4.0)
        val t5: Double = B5 * t.pow(5.0)
        val b = t1 + t2 + t3 + t4 + t5

        val snd = standardNormalDistribution(value) // for testing

        val cd = 1 - snd * b

        var resp: Double = if (value < 0) {
            1 - cd
        } else {
            cd
        }

        return resp
    }

    private fun standardNormalDistribution(x: Double): Double {
        val top = exp(-0.5 * x.pow(2.0))
        val bottom = sqrt(2 * Math.PI)

        return top / bottom
    }
}
