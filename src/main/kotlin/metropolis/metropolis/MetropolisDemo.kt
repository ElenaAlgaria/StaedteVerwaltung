package metropolis.metropolis

import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import metropolis.metropolis.controller.MetropolisController
import metropolis.metropolis.repository.cityRepository
import metropolis.metropolis.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources
import metropolis.metropolis.view.MetropolisWindow

fun main(){

    val url = "/data/metropolisDB".urlFromResources()

    val countryRepository = countryRepository(url)
    val cityRepository = cityRepository(url)

    val controller = MetropolisController(countryRepository, cityRepository)

    application{
        with(controller){
       state.activeCountryExplorerController.uiScope = rememberCoroutineScope()
       state.activeCityExplorerController.uiScope = rememberCoroutineScope()
        MetropolisWindow(state,
            trigger = {triggerAction(it)})
        }
    }

}
