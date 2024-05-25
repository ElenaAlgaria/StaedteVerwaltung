package metropolis.explorer

import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import metropolis.explorer.controller.cityExplorerController
import metropolis.explorer.controller.countryExplorerController
import metropolis.metropolis.repository.cityRepository
import metropolis.explorer.view.ExplorerWindow
import metropolis.metropolis.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources


fun main() {
    val url = "/data/metropolisDB".urlFromResources()

    val countryRepository = countryRepository(url)
    val cityRepository = cityRepository(url)

    val countryController = countryExplorerController(countryRepository)
    val cityController = cityExplorerController(cityRepository)

//    val controller = ExplorerController(countryRepository, cityRepository)

//    val tabIndex = remember { mutableStateOf(0)}

    application {
        with(countryController){
            uiScope = rememberCoroutineScope()
                    ExplorerWindow(
                        state = state,
                        dataProvider = {getData(it) },
                        idProvider = { it.isoNumeric },
                        trigger = {triggerAction(it)},
                        triggerEditor = {}
                    )
        }


//        when (tabIndex.value) {
//            0 ->
//                with(countryController) {
//                    uiScope = rememberCoroutineScope()
//                    ExplorerWindow(
//                        state = state,
//                        dataProvider = { getData(it) },
//                        idProvider = { it.isoNumeric },
//                        trigger = { triggerAction(it) },
//                        tabIndex = tabIndex.value,
//                        tabChange = {tabIndex.value = it},
//                        triggerEditor = {}
//                    )
//                }
//            else ->
//                with(cityController) {
//                    uiScope = rememberCoroutineScope()
//                    ExplorerWindow(
//                        state = state,
//                        dataProvider = { getData(it) },
//                        idProvider = { it.id },
//                        trigger = { triggerAction(it) },
//                        tabIndex = tabIndex.value,
//                        tabChange = {tabIndex.value = it},
//                        triggerEditor = {}
//                    )
//                }
//        }

   }
}
