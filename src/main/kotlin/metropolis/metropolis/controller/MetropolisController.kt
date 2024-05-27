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
            activeCountryEditorController = countryEditorController(4, countryRepository),
            activeCityEditorController = cityEditorController(2960, cityRepository),
            activeCountry = null,
            activeCity = null
        )
    )

    private fun switchToCountryExplorer(id: Int) {
        state = state.copy(
            activeCountryExplorerController = createCountryExplorerController(),
            activeCountry = countryRepository.read(id)
        )
    }

    private fun switchToCityExplorer(nameCity: String, countryCode: String) {

        val idList = cityRepository.readFilteredIds(
            filters = listOf(
                Filter(
                    column = CityColumn.NAME, op = OP.EQ, values = listOf("$nameCity")
                )
            ),
            sortDirective = SortDirective(CityColumn.NAME, SortDirection.ASC)
        )
        if (idList.isNotEmpty()) {
            switchToCityEditor(idList.first())
        }

// hauptstadt? todo
//        state.activeCityExplorerController.executeAction(
//            SetFilter(state.activeCityExplorerController.state.columns[0], nameCity)
//        )


        state.activeCityExplorerController.executeAction(
            SetFilter(state.activeCityExplorerController.state.columns[1], countryCode)
        )


    }

    private fun switchToCountryEditor(id: Int) {
        state = state.copy(
            activeCountryEditorController = createCountryEditorController(id),
            activeCountry = countryRepository.read(id)
        )
    }

    private fun switchToCityEditor(id: Int) {
        state = state.copy(
            activeCityEditorController = createCityEditorController(id),
            activeCity = cityRepository.read(id)
        )
    }

    private fun createCountryExplorerController() =
        countryExplorerController(repository = countryRepository)

    private fun createCountryEditorController(id: Int) =
        countryEditorController(
            repository = countryRepository,
            id = id
        )

    private fun createCityEditorController(id: Int) =
        cityEditorController(
            repository = cityRepository,
            id = id
        )

    fun triggerAction(action: MetropolisAction) {
        when (action) {
            is MetropolisAction.SwitchToCountryEditor -> switchToCountryEditor(action.id)
            is MetropolisAction.SwitchToCityEditor -> switchToCityEditor(action.id)
            is MetropolisAction.SwitchToCountryExplorer -> switchToCountryExplorer(action.id)
            is MetropolisAction.SwitchToCityExplorer -> switchToCityExplorer(action.nameCity, action.countryCode)
        }
    }
}

