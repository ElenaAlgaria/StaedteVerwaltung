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

class MetropolisController(private val countryRepository: CRUDLazyRepository<Country>,
                           private val cityRepository: CRUDLazyRepository<City>) {

    var state by mutableStateOf(MetropolisState(title            = "Metropolis",
        activeExplorerController = countryExplorerController(countryRepository),
        activeEditorController = countryEditorController(4,countryRepository),
        activeCountry = null,
        activeCity = null
    ))

    private fun switchToCountryExplorer(id: Int) {
        state = state.copy(activeExplorerController = createCountryExplorerController(),
            activeCountry             = countryRepository.read(id))
    }

    private fun switchToCityExplorer(id: Int) {
        state = state.copy(activeExplorerController = createCityExplorerController(),
            activeCity             = cityRepository.read(id))
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
        }
    }
}

