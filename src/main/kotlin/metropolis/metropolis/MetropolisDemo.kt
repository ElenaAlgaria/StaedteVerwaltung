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

//    val tabIndex = remember {mutableStateOf(0)}
    // wenn countryController mach 0, wenn cityController 1
//    when (tabIndex.value) {
//        0 ->{}
//
//        else -> {}

//    }

    application{
        with(controller){
       state.activeExplorerController.uiScope = rememberCoroutineScope()
        MetropolisWindow(state,
            trigger = {triggerAction(it)})
        }
    }

}
