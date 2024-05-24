package metropolis.metropolis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var tabIndex by mutableStateOf(0)
    // wenn countryController mach 0, wenn cityController 1
    when (tabIndex) {
        0 ->{}

        else -> {}

    }
    println(controller.state.activeExplorerController.state.title)
    application{
        MetropolisWindow(controller.state,
            trigger = {controller.triggerAction(it)})
    }

}
