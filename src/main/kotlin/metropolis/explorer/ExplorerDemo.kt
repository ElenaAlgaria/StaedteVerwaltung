package metropolis.explorer

import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import metropolis.explorer.controller.cityController
import metropolis.explorer.controller.countryController
import metropolis.metropolis.repository.cityRepository
import metropolis.explorer.view.ExplorerWindow
import metropolis.metropolis.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources


fun main() {
    val url = "/data/metropolisDB".urlFromResources()

    val countryRepository = countryRepository(url)
    val cityRepository = cityRepository(url)

    val countryController = countryController(countryRepository)
    val cityController = cityController(cityRepository)

    var tabIndex by mutableStateOf(0)

    application {
        when (tabIndex) {
            0 ->
                with(countryController) {
                    uiScope = rememberCoroutineScope()
                    ExplorerWindow(
                        state = state,
                        dataProvider = { getData(it) },
                        idProvider = { it.isoNumeric },
                        trigger = { triggerAction(it) },
                        tabIndex = tabIndex,
                        tabChange = {tabIndex = it},
                        triggerEditor = {}
                    )
                }

            else ->
                with(cityController) {
                    uiScope = rememberCoroutineScope()
                    ExplorerWindow(
                        state = state,
                        dataProvider = { getData(it) },
                        idProvider = { it.id },
                        trigger = { triggerAction(it) },
                        tabIndex = tabIndex,
                        tabChange = {tabIndex = it},
                        triggerEditor = {}
                    )
                }
        }
    }
}
