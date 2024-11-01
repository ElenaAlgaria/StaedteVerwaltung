package metropolis.metropolis.repository

import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.repository.CRUDLazyRepository
import metropolis.metropolis.data.Country
import metropolis.metropolis.repository.CountryColumn.AREA_IN_SQKM
import metropolis.metropolis.repository.CountryColumn.ISO_ALPHA2
import metropolis.metropolis.repository.CountryColumn.ISO_NUMERIC
import metropolis.metropolis.repository.CountryColumn.FIPS_CODE
import metropolis.metropolis.repository.CountryColumn.NAME
import metropolis.metropolis.repository.CountryColumn.CAPITAL
import metropolis.metropolis.repository.CountryColumn.POPULATION
import metropolis.metropolis.repository.CountryColumn.CONTINENT
import metropolis.metropolis.repository.CountryColumn.CURRENCY_CODE
import metropolis.metropolis.repository.CountryColumn.CURRENCY_NAME
import metropolis.metropolis.repository.CountryColumn.PHONE
import metropolis.metropolis.repository.CountryColumn.LANGUAGES
import metropolis.metropolis.repository.CountryColumn.NEIGHBOURS
import metropolis.metropolis.repository.CountryColumn.ISO_ALPHA3
import metropolis.metropolis.repository.CountryColumn.GEONAME_ID
import metropolis.xtracted.repository.asSql

enum class CountryColumn : DbColumn {
    ISO_ALPHA2,
    ISO_NUMERIC,
    FIPS_CODE,
    NAME,
    CAPITAL,
    AREA_IN_SQKM,
    POPULATION,
    CONTINENT,
    CURRENCY_CODE,
    CURRENCY_NAME,
    PHONE,
    LANGUAGES,
    NEIGHBOURS,
    ISO_ALPHA3,
    GEONAME_ID
}


fun countryRepository(url: String) =
    CRUDLazyRepository(url = url,
        table = "COUNTRY",
        idColumn = ISO_NUMERIC,
        dataColumns = mapOf(
            ISO_ALPHA2 to { it.isoAlpha2.asSql() },
            ISO_NUMERIC to { it.isoNumeric.toString() },
            FIPS_CODE to { it.fipsCode?.asSql() },
            NAME to { it.name.asSql() },
            CAPITAL to { it.capital?.asSql() },
            AREA_IN_SQKM to { it.areaSqm.toString() },
            POPULATION to { it.population.toString() },
            CONTINENT to { it.continent.asSql() },
            CURRENCY_CODE to { it.currencyCode?.asSql() },
            CURRENCY_NAME to { it.currencyName?.asSql() },
            PHONE to { it.phone?.asSql() },
            LANGUAGES to { it.languages?.asSql() },
            NEIGHBOURS to { it.neighbours?.asSql() },
            ISO_ALPHA3 to {it.isoAlpha3.asSql()},
            GEONAME_ID to {it.geoNameId.toString()}
        ),
        mapper = {
            Country(
                id = getInt("$ISO_NUMERIC"),
                isoAlpha2 = getString("$ISO_ALPHA2"),
                isoNumeric = getInt("$ISO_NUMERIC"),
                fipsCode = getString("$FIPS_CODE"),
                name = getString("$NAME"),
                capital = getString("$CAPITAL"),
                areaSqm = getDouble("$AREA_IN_SQKM"),
                population = getInt("$POPULATION"),
                continent = getString("$CONTINENT"),
                currencyCode = getString("$CURRENCY_CODE"),
                currencyName = getString("$CURRENCY_NAME"),
                phone = getString("$PHONE"),
                languages = getString("$LANGUAGES"),
                neighbours = getString("$NEIGHBOURS"),
                isoAlpha3 = getString("$ISO_ALPHA3"),
                geoNameId = getInt("$GEONAME_ID")
            )
        }
    )
