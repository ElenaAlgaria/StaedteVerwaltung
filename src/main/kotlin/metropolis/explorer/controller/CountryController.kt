package metropolis.explorer.controller

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import metropolis.explorer.data.Country
import metropolis.xtracted.model.DoubleColumn
import metropolis.xtracted.model.IntColumn
import metropolis.xtracted.model.StringColumn
import metropolis.xtracted.repository.LazyRepository
import metropolis.explorer.repository.CountryColumn
import metropolis.xtracted.controller.lazyloading.LazyTableController
import metropolis.xtracted.view.format

private const val ELLIPSES = "..."

fun countryController(repository: LazyRepository<Country>) =
    LazyTableController(title       = "Countries of the World",
                        repository  = repository,
                        defaultItem = Country(ELLIPSES, ELLIPSES, 0, ELLIPSES, ELLIPSES, ELLIPSES,
                            0.0, 0, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES, ELLIPSES,
                            ELLIPSES, ELLIPSES, 0, ELLIPSES, ELLIPSES),
                        columns     = listOf(StringColumn(header        = "Name",
                                                          width         = 250.dp,
                                                          alignment     = Alignment.CenterStart,
                                                          fixed         = true,
                                                          dbColumn      = CountryColumn.NAME,
                                                          valueProvider = { it.name }
                                                         ),
                                             StringColumn(header        = "ISO2",
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

