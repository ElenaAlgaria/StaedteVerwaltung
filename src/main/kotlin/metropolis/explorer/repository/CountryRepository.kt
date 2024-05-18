package metropolis.explorer.repository

import metropolis.xtracted.data.DbColumn
import metropolis.xtracted.repository.LazyRepository
import metropolis.explorer.data.Country
import metropolis.explorer.repository.CountryColumn.AREA_IN_SQKM
import metropolis.explorer.repository.CountryColumn.ISO_ALPHA2
import metropolis.explorer.repository.CountryColumn.ISO_ALPHA3
import metropolis.explorer.repository.CountryColumn.ISO_NUMERIC
import metropolis.explorer.repository.CountryColumn.FIPS_CODE
import metropolis.explorer.repository.CountryColumn.NAME
import metropolis.explorer.repository.CountryColumn.CAPITAL
import metropolis.explorer.repository.CountryColumn.POPULATION
import metropolis.explorer.repository.CountryColumn.CONTINENT
import metropolis.explorer.repository.CountryColumn.TLD
import metropolis.explorer.repository.CountryColumn.CURRENCY_CODE
import metropolis.explorer.repository.CountryColumn.CURRENCY_NAME
import metropolis.explorer.repository.CountryColumn.PHONE
import metropolis.explorer.repository.CountryColumn.POSTAL_CODE_FORMAT
import metropolis.explorer.repository.CountryColumn.POSTAL_CODE_REGEX
import metropolis.explorer.repository.CountryColumn.LANGUAGES
import metropolis.explorer.repository.CountryColumn.GEONAME_ID
import metropolis.explorer.repository.CountryColumn.NEIGHBOURS
import metropolis.explorer.repository.CountryColumn.EQUIVALENT_FIPS_CODE
import metropolis.xtracted.repository.asSql

enum class CountryColumn : DbColumn {
    ISO_ALPHA2,
    ISO_ALPHA3,
    ISO_NUMERIC,
    FIPS_CODE,
    NAME,
    CAPITAL,
    AREA_IN_SQKM,
    POPULATION,
    CONTINENT,
    TLD,
    CURRENCY_CODE,
    CURRENCY_NAME,
    PHONE,
    POSTAL_CODE_FORMAT,
    POSTAL_CODE_REGEX,
    LANGUAGES,
    GEONAME_ID,
    NEIGHBOURS,
    EQUIVALENT_FIPS_CODE,

}


// eine Hilfs-Funktion, die das LazyRepository für Countries konfiguriert.

fun countryRepository(url: String) =
    LazyRepository(url = url,
        table = "COUNTRY",
        dataColumns = mapOf(
            ISO_ALPHA2 to { it.isoAlpha2.asSql() },
            ISO_ALPHA3 to { it.isoAlpha3.asSql() },
            ISO_NUMERIC to { it.isoNumeric.toString() },
            FIPS_CODE to { it.fipsCode?.asSql() },
            NAME to { it.name.asSql() },
            CAPITAL to { it.capital.toString() },
            AREA_IN_SQKM to { it.areaSqm.toString() },
            POPULATION to { it.population.toString() },
            CONTINENT to { it.continent.asSql() },
            TLD to { it.tld?.asSql() },
            CURRENCY_CODE to { it.currencyCode?.asSql() },
            CURRENCY_NAME to { it.currencyName?.asSql() },
            PHONE to { it.phone?.asSql() },
            POSTAL_CODE_FORMAT to { it.postalCodeFormat?.asSql() },
            POSTAL_CODE_REGEX to { it.postalCodeRegex?.asSql() },
            LANGUAGES to { it.languages?.asSql() },
            GEONAME_ID to { it.geoNameId.toString() },
            NEIGHBOURS to { it.neighbours?.asSql() },
            EQUIVALENT_FIPS_CODE to { it.equivalentFipsCode?.asSql() },
        ),
        idColumn = ISO_NUMERIC,
        mapper = {
            Country(
                id = getInt("${ISO_NUMERIC}"),
                isoAlpha2 = getString("$ISO_ALPHA2"),
                isoAlpha3 = getString("$ISO_ALPHA3"),
                isoNumeric = getInt("$ISO_NUMERIC"),
                fipsCode = getString("$FIPS_CODE"),
                name = getString("$NAME"),
                capital = getString("$CAPITAL"),
                areaSqm = getDouble("$AREA_IN_SQKM"),
                population = getInt("$POPULATION"),
                continent = getString("$CONTINENT"),
                tld = getString("$TLD"),
                currencyCode = getString("$CURRENCY_CODE"),
                currencyName = getString("$CURRENCY_NAME"),
                phone = getString("$PHONE"),
                postalCodeFormat = getString("$POSTAL_CODE_FORMAT"),
                postalCodeRegex = getString("$POSTAL_CODE_REGEX"),
                languages = getString("$LANGUAGES"),
                geoNameId = getInt("$GEONAME_ID"),
                neighbours = getString("$NEIGHBOURS"),
                equivalentFipsCode = getString("$EQUIVALENT_FIPS_CODE")
            )
        }
    )
