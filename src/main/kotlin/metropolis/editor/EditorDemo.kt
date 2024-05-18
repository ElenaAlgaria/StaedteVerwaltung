package metropolis.editor

import androidx.compose.ui.window.application
import metropolis.editor.controller.editorController
import metropolis.editor.view.EditorWindow
import metropolis.metropolis.repository.cityRepository
import metropolis.metropolis.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources

fun main() {
    val url = "/data/metropolisDB".urlFromResources()
    val countryRepository = countryRepository(url)
    val cityRepository = cityRepository(url)
    val funId = 4

    val controller = editorController(funId, countryRepository)

    application {
        EditorWindow(state     = controller.state,
            undoState = controller.undoState,
            trigger   = { controller.triggerAction(it) })
    }
}
