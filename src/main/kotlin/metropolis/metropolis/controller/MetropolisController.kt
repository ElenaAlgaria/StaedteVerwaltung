package metropolis.metropolis.controller

import androidx.compose.runtime.mutableStateOf
import metropolis.editor.controller.countryEditorController
import metropolis.explorer.controller.countryExplorerController
import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.repository.CRUDLazyRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import metropolis.editor.controller.cityEditorController
import metropolis.explorer.controller.cityExplorerController
import metropolis.metropolis.repository.CityColumn
import metropolis.metropolis.repository.CountryColumn
import metropolis.xtracted.controller.lazyloading.LazyTableAction
import metropolis.xtracted.controller.lazyloading.LazyTableAction.SetFilter
import metropolis.xtracted.data.Filter
import metropolis.xtracted.data.OP
import metropolis.xtracted.data.SortDirection
import metropolis.xtracted.data.SortDirective


class MetropolisController(
    private val countryRepository: CRUDLazyRepository<Country>,
    private val cityRepository: CRUDLazyRepository<City>
) {

    var state by mutableStateOf(
        MetropolisState(
            title = "Metropolis",
            activeCountryExplorerController = countryExplorerController(countryRepository),
            activeCityExplorerController = cityExplorerController(cityRepository),
            activeCountryEditorController = countryEditorController(4, countryRepository, {}, {}),
            activeCityEditorController = cityEditorController(2960, cityRepository, {}, {}),
            activeCountry = null,
            activeCity = null
        )
    )

    private fun switchToCountryExplorer(isoAlpha2: String) {
        val idList = countryRepository.readFilteredIds(
            filters = listOf(
                Filter(
                    column = CountryColumn.ISO_ALPHA2,
                    op = OP.EQ, values = listOf("$isoAlpha2")
                )
            ),
            sortDirective = SortDirective(CountryColumn.ISO_ALPHA2, SortDirection.ASC), ""
        )
        switchToCountryEditor(idList.first())

        state.activeCountryExplorerController.executeAction(
            SetFilter(state.activeCountryExplorerController.state.columns[1], isoAlpha2, "")
        )
    }

    private fun switchToCityExplorer(nameCity: String, countryCode: String) {

        val idList = cityRepository.readFilteredIds(
            filters = listOf(
                Filter(
                    column = CityColumn.NAME, op = OP.EQ, values = listOf("$nameCity")
                )
            ),
            sortDirective = SortDirective(CityColumn.NAME, SortDirection.ASC),
            ""
        )
        if (idList.isNotEmpty()) {
            switchToCityEditor(idList.first())
        }

        state.activeCityExplorerController.executeAction(
            SetFilter(state.activeCityExplorerController.state.columns[1], countryCode, nameCity)
        )

    }

    private fun switchToCountryEditor(id: Int) {
        if (id < 0) {
            val data = Country(
                id, isoNumeric = id, isoAlpha3 = "", isoAlpha2 = "",
                name = "", areaSqm = 0.0, population = 0, currencyCode = "", continent = "", geoNameId = 0,
            )
            val key = countryRepository.createKey(data)
            state = state.copy(
                activeCountryEditorController = createCountryEditorController(key),
                activeCountry = countryRepository.read(key)
            )

            state.activeCountryExplorerController.triggerAction(LazyTableAction.Reload(key))

        } else {
            state = state.copy(
                activeCountryEditorController = createCountryEditorController(id),
                activeCountry = countryRepository.read(id)
            )
        }
    }

    private fun switchToCityEditor(id: Int) {
        if (id < 0) {
            val data = City(
                id, name = "", latitude = 0.0, longitude = 0.0,
                featureCode = "", featureClass = "", countryCode = "", admin1Code = "", population = 0, elevation = 0,
                dem = 0, timezone = "", modificationDate = ""
            )
            val key = cityRepository.createKey(data)
            state = state.copy(
                activeCityEditorController = createCityEditorController(key),
                activeCity = cityRepository.read(key)
            )
            state.activeCityExplorerController.triggerAction(LazyTableAction.Reload(key))
        } else {
            state = state.copy(
                activeCityEditorController = createCityEditorController(id),
                activeCity = cityRepository.read(id)
            )
        }
    }

    private fun createCountryEditorController(id: Int) =
        countryEditorController(
            repository = countryRepository,
            id = id,
            onDeleted = {
                switchToCountryEditor(4)
                state.activeCountryExplorerController.triggerAction(LazyTableAction.Reload(null))
                state.activeCityExplorerController.triggerAction(LazyTableAction.Reload(null))
            },
            onSave = { state.activeCountryExplorerController.triggerAction(LazyTableAction.Reload(id))}
        )

    private fun createCityEditorController(id: Int) =
        cityEditorController(
            repository = cityRepository,
            id = id,
            onDeleted = {
                switchToCityEditor(2960)
                state.activeCityExplorerController.triggerAction(LazyTableAction.Reload(null))
            },
            onSave = {state.activeCityExplorerController.triggerAction(LazyTableAction.Reload(id))  }

        )


    fun triggerAction(action: MetropolisAction) {
        when (action) {
            is MetropolisAction.SwitchToCountryEditor -> switchToCountryEditor(action.id)
            is MetropolisAction.SwitchToCityEditor -> switchToCityEditor(action.id)
            is MetropolisAction.SwitchToCountryExplorer -> switchToCountryExplorer(action.isoAlpha2)
            is MetropolisAction.SwitchToCityExplorer -> switchToCityExplorer(action.nameCity, action.countryCode)
        }
    }
}

