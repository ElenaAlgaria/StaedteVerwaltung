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

    application {
        with(countryController){
            uiScope = rememberCoroutineScope()
                    ExplorerWindow(
                        state = state,
                        dataProvider = {getData(it) },
                        idProvider = { it.isoNumeric },
                        trigger = {triggerAction(it)},
                        triggerEditor = {},
                        triggerExplorer = {},
                        triggerCreate = {}
                    )
        }
   }
}
