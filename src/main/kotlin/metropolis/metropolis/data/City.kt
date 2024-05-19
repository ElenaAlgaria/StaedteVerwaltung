package metropolis.metropolis.data

import java.util.Date
import metropolis.xtracted.repository.Identifiable

data class City (
    override val id     : Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
    val admin1Code : String,
    val population : Int,
    val elevation : Int?,
    val dem : Int,
    val timezone : String
) : Identifiable
