package metropolis.metropolis.data

import metropolis.xtracted.repository.Identifiable

data class Country(
    override val id: Int,
    val isoAlpha2: String,
    val isoNumeric: Int,
    val fipsCode: String? = null,
    val name: String,
    val capital: String? = null,
    val areaSqm: Double,
    val population: Int,
    val continent: String,
    val currencyCode: String? = null,
    val currencyName: String? = null,
    val phone: String? = null,
    val languages: String? = null,
    val neighbours: String? = null,
) : Identifiable
