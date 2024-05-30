package metropolis.xtracted.controller.lazyloading

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

import metropolis.xtracted.controller.Action
import metropolis.xtracted.data.SortDirective
import metropolis.xtracted.model.TableColumn

sealed class LazyTableAction(
        override val name   : String,
        override val icon   : ImageVector? = null,
        override val enabled: Boolean = true) : Action {

    class Select(val id: Int)                                             : LazyTableAction("Select Item")
    class ToggleSortOrder<T>(val column: TableColumn<T, *>)               : LazyTableAction("Change Sort Order")
    class SetFilter<T>(val column: TableColumn<T, *>, val filter: String, val nameOrder: String) : LazyTableAction("Set Filter")
    object SelectNext                                                     : LazyTableAction("Select Next Item")
    object SelectPrevious                                                 : LazyTableAction("Select Next Item")
    object Create                                                 : LazyTableAction("Create", Icons.Filled.Add)
    class Reload(val reloadId: Int?)                                                 : LazyTableAction("Reload")
}
