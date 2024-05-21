package metropolis.metropolis.controller

import androidx.compose.runtime.mutableStateOf
import metropolis.editor.controller.countryEditorController
import metropolis.explorer.controller.countryController
import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.repository.CRUDLazyRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import metropolis.editor.controller.cityEditorController
import metropolis.xtracted.model.EditorState

class MetropolisController(private val countryRepository: CRUDLazyRepository<Country>,
                           private val cityRepository: CRUDLazyRepository<City>) {

    var state by mutableStateOf(MetropolisState(title            = "Metropolis",
        activeExplorerController = countryController(countryRepository),
        activeEditorController = countryEditorController(4,countryRepository),
        activeCountry = null,
        activeCity = null
    ))

    private fun switchToCountryEditor(id: Int) {
        state = state.copy(activeEditorController = createCountryEditorController(id),
        activeCountry             = countryRepository.read(id))
    }

//    private fun switchToCityEditor(id: Int) {
//        state = state.copy(activeEditorController = createCityEditorController(id),
//        activeCity             = cityRepository.read(id))
//    }

// onLoggedIn = { switchToWorkReport(it) })
//
    private fun createCountryEditorController(id: Int) =
        countryEditorController(repository       = countryRepository,
            id = id )

    private fun createCityEditorController(id: Int) =
        cityEditorController(repository       = cityRepository,
            id = id )
}

