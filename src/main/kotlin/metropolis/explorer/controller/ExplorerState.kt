package metropolis.explorer.controller

import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.controller.editor.EditorController
import metropolis.xtracted.controller.lazyloading.LazyTableController
import metropolis.xtracted.repository.Identifiable

//versuch
data class ExplorerState(
    val countryExplorer: LazyTableController<Country>,
    val cityExplorer: LazyTableController<City>
)

