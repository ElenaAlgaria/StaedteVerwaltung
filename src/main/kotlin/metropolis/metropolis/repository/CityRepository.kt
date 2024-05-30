package metropolis.metropolis.repository

import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.repository.CRUDLazyRepository
import metropolis.metropolis.data.City
import metropolis.metropolis.repository.CityColumn.ID
import metropolis.metropolis.repository.CityColumn.NAME
import metropolis.metropolis.repository.CityColumn.LATITUDE
import metropolis.metropolis.repository.CityColumn.LONGITUDE
import metropolis.metropolis.repository.CityColumn.COUNTRY_CODE
import metropolis.metropolis.repository.CityColumn.ADMIN1_CODE
import metropolis.metropolis.repository.CityColumn.POPULATION
import metropolis.metropolis.repository.CityColumn.ELEVATION
import metropolis.metropolis.repository.CityColumn.DEM
import metropolis.metropolis.repository.CityColumn.TIMEZONE
import metropolis.metropolis.repository.CityColumn.MODIFICATION_DATE
import metropolis.metropolis.repository.CityColumn.FEATURE_CODE
import metropolis.metropolis.repository.CityColumn.FEATURE_CLASS
import metropolis.xtracted.repository.asSql


enum class CityColumn : DbColumn {
    ID,
    NAME,
    LATITUDE,
    LONGITUDE,
    COUNTRY_CODE,
    ADMIN1_CODE,
    POPULATION,
    ELEVATION,
    DEM,
    TIMEZONE,
    MODIFICATION_DATE,
    FEATURE_CODE,
    FEATURE_CLASS
}

fun cityRepository(url: String) =
    CRUDLazyRepository(url = url,
        table = "CITY",
        idColumn = ID,
        dataColumns = mapOf(
            ID to {it.id.toString()},
            NAME to { it.name.asSql() },
            LATITUDE to { it.longitude.toString() },
            LONGITUDE to { it.longitude.toString() },
            COUNTRY_CODE to { it.countryCode.asSql() },
            ADMIN1_CODE to { it.admin1Code.asSql() },
            POPULATION to { it.population.toString() },
            ELEVATION to { it.elevation?.toString() },
            DEM to { it.dem.toString() },
            TIMEZONE to { it.timezone.asSql() },
            MODIFICATION_DATE to {it.modificationDate.asSql()},
            FEATURE_CODE to {it.featureCode.asSql()},
            FEATURE_CLASS to {it.featureClass.asSql()}

        ),
        mapper = {
            City(
                id = getInt("$ID"),
                name = getString("$NAME"),
                latitude = getDouble("$LATITUDE"),
                longitude = getDouble("$LONGITUDE"),
                countryCode = getString("$COUNTRY_CODE"),
                admin1Code = getString("$ADMIN1_CODE"),
                population = getInt("$POPULATION"),
                elevation = getInt("$ELEVATION"),
                dem = getInt("$DEM"),
                timezone = getString("$TIMEZONE"),
                modificationDate = getString("${MODIFICATION_DATE}"),
                featureCode = getString("${FEATURE_CODE}"),
                featureClass = getString("${FEATURE_CLASS}")
                )
        }
    )


