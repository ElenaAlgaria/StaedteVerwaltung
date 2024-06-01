package metropolis.editor

import androidx.compose.ui.window.application
import metropolis.editor.controller.cityEditorController
import metropolis.editor.controller.countryEditorController
import metropolis.editor.view.EditorWindow
import metropolis.metropolis.repository.cityRepository
import metropolis.metropolis.repository.countryRepository
import metropolis.xtracted.repository.urlFromResources
import metropolis.xtracted.repository.urlFromWorkingDirectory

fun main() {
    val url = "/data/metropolisDB".urlFromWorkingDirectory()
    val countryRepository = countryRepository(url)
    val cityRepository = cityRepository(url)
    val countryId = 380
    val cityId = 2960

    //Notiz für die Dozenten: Controller switchen für die Ansicht des anderen Editors
    val countryEditorController = countryEditorController(countryId, countryRepository, {}, {})
    val cityEditorController = cityEditorController(cityId, cityRepository, {}, {})

    application {
        EditorWindow(state     = countryEditorController.state,
            undoState = countryEditorController.undoState,
            trigger   = { countryEditorController.triggerAction(it) })
    }
}
