package model

data class EuroVanilla(
    val underlying: Double,
    val strike: Double,
    val tenor: Double,
    val sigma: Double
)
