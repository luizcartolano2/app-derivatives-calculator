package model

import io.kotest.core.spec.style.BehaviorSpec

class EuroVanillaPriceTest : BehaviorSpec() {
    private val euroVanillaPriceCalculator = EuroVanillaPrice()
    private val spotValue = 100.0
}
