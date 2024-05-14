package metropolis.explorer

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.application
import metropolis.explorer.controller.countryController
import metropolis.explorer.view.ExplorerWindow
import metropolis.explorer.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources



fun main() {

    val url        = "/data/metropolisDB".urlFromResources()
    val countryRepository = countryRepository(url)
    val countryController = countryController(countryRepository)

    application {
        with(countryController){
            uiScope = rememberCoroutineScope()
            ExplorerWindow(state        = state,
                dataProvider = { getData(it) },
                idProvider   = { it.isoNumeric },
                trigger      = { triggerAction(it) }
            )
        }
    }
}
