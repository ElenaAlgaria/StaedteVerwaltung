package metropolis.metropolis.controller

import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.controller.ControllerBase
import metropolis.xtracted.controller.editor.EditorController
import metropolis.xtracted.controller.lazyloading.LazyTableController
import metropolis.xtracted.repository.Identifiable

data class MetropolisState(
    val title: String,
    val activeExplorerController: LazyTableController<*>,
    val activeEditorController: EditorController<*>,
    val activeCountry: Country?,
    val activeCity: City?
)