package metropolis.xtracted.controller.editor

import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import metropolis.xtracted.controller.Action
import metropolis.xtracted.model.Attribute

sealed class EditorAction(
        override val name   : String,
        override val icon   : ImageVector? = null,
        override val enabled: Boolean      = true) : Action {

    class  Update(val attribute: Attribute<*>, val value: String) : EditorAction("Update ${attribute.id.translate(Locale.ENGLISH)}", null, !attribute.readOnly)

    class  Save(enabled: Boolean)        : EditorAction("Save",   Icons.Filled.Save,   enabled)
    object Reload                        : EditorAction("Reload", Icons.Filled.Cached)
    object Delete : EditorAction("Delete", icon = Icons.Filled.Delete)

    class  Undo(enabled: Boolean = true) : EditorAction("Undo", Icons.Filled.Undo, enabled)
    class  Redo(enabled: Boolean = true) : EditorAction("Redo", Icons.Filled.Redo, enabled)

    class  SetLocale(val locale: Locale, enabled: Boolean) : EditorAction(locale.isO3Language, null, enabled)


}
