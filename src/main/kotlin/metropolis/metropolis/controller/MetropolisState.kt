package metropolis.metropolis.controller

import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.controller.editor.EditorController
import metropolis.xtracted.controller.lazyloading.LazyTableController

data class MetropolisState(
    val title: String,
    val activeCountryExplorerController: LazyTableController<Country>,
    val activeCityExplorerController: LazyTableController<City>,
    val activeCountryEditorController: EditorController<Country>,
    val activeCityEditorController: EditorController<City>,
    val activeCountry: Country?,
    val activeCity: City?
)
