package metropolis.explorer.repository

import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.repository.LazyRepository
import metropolis.explorer.data.City
import metropolis.explorer.repository.CityColumn.ID
import metropolis.explorer.repository.CityColumn.NAME
import metropolis.explorer.repository.CityColumn.ASCII_NAME
import metropolis.explorer.repository.CityColumn.ALTERNATE_NAMES
import metropolis.explorer.repository.CityColumn.LATITUDE
import metropolis.explorer.repository.CityColumn.LONGITUDE
import metropolis.explorer.repository.CityColumn.FEATURE_CLASS
import metropolis.explorer.repository.CityColumn.FEATURE_CODE
import metropolis.explorer.repository.CityColumn.COUNTRY_CODE
import metropolis.explorer.repository.CityColumn.CC2
import metropolis.explorer.repository.CityColumn.ADMIN1_CODE
import metropolis.explorer.repository.CityColumn.ADMIN2_CODE
import metropolis.explorer.repository.CityColumn.ADMIN3_CODE
import metropolis.explorer.repository.CityColumn.ADMIN4_CODE
import metropolis.explorer.repository.CityColumn.POPULATION
import metropolis.explorer.repository.CityColumn.ELEVATION
import metropolis.explorer.repository.CityColumn.DEM
import metropolis.explorer.repository.CityColumn.TIMEZONE
import metropolis.explorer.repository.CityColumn.MODIFICATION_DATE
import metropolis.xtracted.repository.asSql


enum class CityColumn : DbColumn {
    ID,
    NAME,
    ASCII_NAME,
    ALTERNATE_NAMES,
    LATITUDE,
    LONGITUDE,
    FEATURE_CLASS,
    FEATURE_CODE,
    COUNTRY_CODE,
    CC2,
    ADMIN1_CODE,
    ADMIN2_CODE,
    ADMIN3_CODE,
    ADMIN4_CODE,
    POPULATION,
    ELEVATION,
    DEM,
    TIMEZONE,
    MODIFICATION_DATE
}

fun cityRepository(url: String) =
    LazyRepository(url = url,
        table = "CITY",
        idColumn = ID,
        dataColumns = mapOf(
            ID to {it.id.toString()},
            NAME to { it.name.asSql() },
            ASCII_NAME to { it.asciiName.asSql() },
            ALTERNATE_NAMES to { it.alternateNames?.asSql() },
            LATITUDE to { it.longitude.toString() },
            LONGITUDE to { it.longitude.toString() },
            FEATURE_CLASS to { it.featureClass.asSql() },
            FEATURE_CODE to { it.featureCode.asSql() },
            COUNTRY_CODE to { it.countryCode.asSql() },
            CC2 to { it.cc2?.asSql() },
            ADMIN1_CODE to { it.admin1Code.asSql() },
            ADMIN2_CODE to { it.admin2Code?.asSql() },
            ADMIN3_CODE to { it.admin3Code?.asSql() },
            ADMIN4_CODE to { it.admin4Code?.asSql() },
            POPULATION to { it.population.toString() },
            ELEVATION to { it.elevation?.toString() },
            DEM to { it.dem.toString() },
            TIMEZONE to { it.timezone.asSql() },
            MODIFICATION_DATE to { it.modificationDate.asSql() }
        ),
        mapper = {
            City(
                id = getInt("$ID"),
                name = getString("${NAME}"),
                asciiName = getString("${ASCII_NAME}"),
                alternateNames = getString("${ALTERNATE_NAMES}"),
                latitude = getDouble("${LATITUDE}"),
                longitude = getDouble("${LONGITUDE}"),
                featureClass = getString("${FEATURE_CLASS}"),
                featureCode = getString("${FEATURE_CODE}"),
                countryCode = getString("${COUNTRY_CODE}"),
                cc2 = getString("${CC2}"),
                admin1Code = getString("${ADMIN1_CODE}"),
                admin2Code = getString("${ADMIN2_CODE}"),
                admin3Code = getString("${ADMIN3_CODE}"),
                admin4Code = getString("${ADMIN4_CODE}"),
                population = getInt("${POPULATION}"),
                elevation = getInt("${ELEVATION}"),
                dem = getInt("${DEM}"),
                timezone = getString("${TIMEZONE}"),
                modificationDate = getString("${MODIFICATION_DATE}")
                )
        }
    )


