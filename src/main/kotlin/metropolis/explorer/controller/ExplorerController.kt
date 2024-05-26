package metropolis.explorer.controller

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.metropolis.repository.CityColumn
import metropolis.xtracted.model.DoubleColumn
import metropolis.xtracted.model.IntColumn
import metropolis.xtracted.model.StringColumn
import metropolis.xtracted.repository.CRUDLazyRepository
import metropolis.metropolis.repository.CountryColumn
import metropolis.xtracted.controller.lazyloading.LazyTableController
import metropolis.xtracted.view.format

private const val ELLIPSES = "..."
//class ExplorerController(private val countryRepository: CRUDLazyRepository<Country>,
//                         private val cityRepository: CRUDLazyRepository<City>){

//var state by mutableStateOf(ExplorerState(countryController(countryRepository), cityController(cityRepository)))

    fun countryExplorerController(repository: CRUDLazyRepository<Country>) =
        LazyTableController(title       = "Countries of the World",
            repository  = repository,
            defaultItem = Country(0,ELLIPSES, 0, ELLIPSES, ELLIPSES, ELLIPSES, 0.0,
                0, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES),
            columns     = listOf(StringColumn(header        = "Name",
                width         = 250.dp,
                alignment     = Alignment.CenterStart,
                fixed         = true,
                dbColumn      = CountryColumn.NAME,
                valueProvider = { it.name }
            ),
                StringColumn(header        = "ISO Alpha 2",
                    width         = 80.dp,
                    alignment     = Alignment.Center,
                    fixed         = false,
                    dbColumn      = CountryColumn.ISO_ALPHA2,
                    valueProvider = { it.isoAlpha2 }
                ),
                StringColumn(header        = "Capital",
                    width         = 250.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = true,
                    dbColumn      = CountryColumn.CAPITAL,
                    valueProvider = { it.capital }
                ),
                DoubleColumn(header        = "Area ( km² )",
                    width         = 120.dp,
                    alignment     = Alignment.CenterEnd,
                    fixed         = false,
                    dbColumn      = CountryColumn.AREA_IN_SQKM,
                    valueProvider = { it.areaSqm },
                    formatter     = { it.format("%,.1f") }
                ),
                IntColumn(header        = "Population",
                    width         = 120.dp,
                    alignment     = Alignment.CenterEnd,
                    fixed         = false,
                    dbColumn      = CountryColumn.POPULATION,
                    valueProvider = { it.population },
                    formatter     = { it.format("%,d") }
                ),
                StringColumn(header        = "Continent",
                    width         = 100.dp,
                    alignment     = Alignment.Center,
                    fixed         = false,
                    dbColumn      = CountryColumn.CONTINENT,
                    valueProvider = { it.continent }
                ),
                StringColumn(header        = "Currency",
                    width         = 120.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = false,
                    dbColumn      = CountryColumn.CURRENCY_NAME,
                    valueProvider = { it.currencyName }
                ),
                StringColumn(header        = "Ccy",
                    width         = 80.dp,
                    alignment     = Alignment.Center,
                    fixed         = false,
                    dbColumn      = CountryColumn.CURRENCY_CODE,
                    valueProvider = { it.currencyCode }
                ),
                StringColumn(header        = "Neighbours",
                    width         = 200.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = false,
                    dbColumn      = CountryColumn.NEIGHBOURS,
                    valueProvider = { it.neighbours }
                )
            )
        )

    fun cityExplorerController(repository: CRUDLazyRepository<City>) =
        LazyTableController(title       = "Cities of the World",
            repository  = repository,
            defaultItem = City(id = 0, ELLIPSES, 0.0, 0.0,  ELLIPSES,
                ELLIPSES,  0, 0,0,
                ELLIPSES
            ),
            columns     = listOf(StringColumn(header        = "Name",
                width         = 250.dp,
                alignment     = Alignment.CenterStart,
                fixed         = true,
                dbColumn      = CityColumn.NAME,
                valueProvider = { it.name }
            ),
                StringColumn(header        = "Country Code",
                    width         = 250.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = true,
                    dbColumn      = CityColumn.COUNTRY_CODE,
                    valueProvider = { it.countryCode }
                ),
                StringColumn(header        = "Timezone",
                    width         = 250.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = false,
                    dbColumn      = CityColumn.TIMEZONE,
                    valueProvider = { it.timezone }
                ),
                DoubleColumn(header        = "LATITUDE",
                    width         = 80.dp,
                    alignment     = Alignment.Center,
                    fixed         = false,
                    dbColumn      = CityColumn.LATITUDE,
                    valueProvider = { it.latitude }
                ),
                DoubleColumn(header        = "Longitude",
                    width         = 250.dp,
                    alignment     = Alignment.CenterStart,
                    fixed         = false,
                    dbColumn      = CityColumn.LONGITUDE,
                    valueProvider = { it.longitude}
                ),
                StringColumn(header        = "Admin 1 Code",
                    width         = 120.dp,
                    alignment     = Alignment.CenterEnd,
                    fixed         = false,
                    dbColumn      = CityColumn.ADMIN1_CODE,
                    valueProvider = { it.admin1Code }
                ),
                IntColumn(header        = "Population",
                    width         = 120.dp,
                    alignment     = Alignment.CenterEnd,
                    fixed         = false,
                    dbColumn      = CityColumn.POPULATION,
                    valueProvider = { it.population },
                    formatter     = { it.format("%,d") }
                ),
                IntColumn(header        = "Elevation",
                    width         = 120.dp,
                    alignment     = Alignment.CenterEnd,
                    fixed         = false,
                    dbColumn      = CityColumn.ELEVATION,
                    valueProvider = { it.elevation },
                    formatter     = { it.format("%,d") }
                ),

                )
        )


//}

