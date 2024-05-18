package metropolis.explorer.data

import metropolis.xtracted.repository.Identifiable

data class Country(
    override val id: Int,
    val isoAlpha2: String,
    val isoAlpha3: String,
    val isoNumeric: Int,
    val fipsCode: String?,
    val name: String,
    val capital: String?,
    val areaSqm: Double,
    val population: Int,
    val continent: String,
    val tld: String?,
    val currencyCode: String?,
    val currencyName: String?,
    val phone: String?,
    val postalCodeFormat: String?,
    val postalCodeRegex: String?,
    val languages: String?,
    val geoNameId: Int,
    val neighbours: String?,
    val equivalentFipsCode: String?
) : Identifiable
