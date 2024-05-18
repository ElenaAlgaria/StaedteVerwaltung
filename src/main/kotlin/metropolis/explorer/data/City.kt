package metropolis.explorer.data

import java.util.Date
import metropolis.xtracted.repository.Identifiable

data class City (
    override val id     : Int,
    val name: String,
    val asciiName: String,
    val alternateNames: String?,
    val latitude: Double,
    val longitude: Double,
    val featureClass : String,
    val featureCode : String,
    val countryCode: String,
    val cc2 : String?,
    val admin1Code : String,
    val admin2Code : String?,
    val admin3Code : String?,
    val admin4Code : String?,
    val population : Int,
    val elevation : Int?,
    val dem : Int,
    val timezone : String,
    val modificationDate : String
) : Identifiable
