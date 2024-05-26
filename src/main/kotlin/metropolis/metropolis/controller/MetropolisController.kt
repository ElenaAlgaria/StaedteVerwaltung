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
import metropolis.xtracted.data.Filter
import metropolis.xtracted.data.OP
import metropolis.xtracted.data.SortDirection
import metropolis.xtracted.data.SortDirective

class MetropolisController(private val countryRepository: CRUDLazyRepository<Country>,
                           private val cityRepository: CRUDLazyRepository<City>) {

    var state by mutableStateOf(MetropolisState(title            = "Metropolis",
        activeCountryExplorerController = countryExplorerController(countryRepository),
        activeCityExplorerController = cityExplorerController(cityRepository),
        activeEditorController = countryEditorController(4,countryRepository),
        activeCountry = null,
        activeCity = null
    ))

    private fun switchToCountryExplorer(id: Int) {
        state = state.copy(activeCountryExplorerController = createCountryExplorerController(),
            activeCountry             = countryRepository.read(id))
    }

    private fun switchToCityExplorer(id: Int) {
      // liste mit Integer:
//       cityRepository.readFilteredIds(filters = listOf(Filter(
//            column = CityColumn.NAME, op = OP.EQ, values = listOf("Pristina"))),
//            sortDirective = SortDirective( CityColumn.NAME,SortDirection.ASC))

        // alli städt vo eim land zbsp schwiiz:
//      println(cityRepository.readFilteredIds(filters = listOf(Filter(
//            column = CityColumn.COUNTRY_CODE, op = OP.EQ, values = listOf("CH"))),
//            sortDirective = SortDirective( CityColumn.COUNTRY_CODE,SortDirection.ASC)))

        state = state.copy(activeCityExplorerController = createCityExplorerController(),
            activeCity             = cityRepository.read(2658649))

        println("und tschüss")
    }

    private fun switchToCountryEditor(id: Int) {
        state = state.copy(activeEditorController = createCountryEditorController(id),
        activeCountry             = countryRepository.read(id))
    }

    private fun switchToCityEditor(id: Int) {
        state = state.copy(activeEditorController = createCityEditorController(id),
        activeCity             = cityRepository.read(id))
    }

    private fun createCountryExplorerController() =
        countryExplorerController(repository       = countryRepository)

    private fun createCityExplorerController() =
        cityExplorerController(repository       = cityRepository)

    private fun createCountryEditorController(id: Int) =
        countryEditorController(repository       = countryRepository,
            id = id )

    private fun createCityEditorController(id: Int) =
        cityEditorController(repository       = cityRepository,
            id = id )

    fun triggerAction(action: MetropolisAction) {
        when(action){
            is MetropolisAction.SwitchToCountryEditor -> switchToCountryEditor(action.id)
            is MetropolisAction.SwitchToCityEditor -> switchToCityEditor(action.id)
            is MetropolisAction.SwitchToCountryExplorer -> switchToCountryExplorer(action.id)
            is MetropolisAction.SwitchToCityExplorer -> switchToCityExplorer(action.id)
        }
    }
}

