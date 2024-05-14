package metropolis.xtracted.controller.undo


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.ui.graphics.vector.ImageVector
import metropolis.xtracted.controller.Action

sealed class UndoAction (
        override val name   : String,
        override val icon   : ImageVector? = null,
        override val enabled: Boolean) : Action {

    class Undo<S>(enabled: Boolean = true, val updateApplicationState: (S) -> Unit) : UndoAction("Undo", Icons.Filled.Undo, enabled)
    class Redo<S>(enabled: Boolean = true, val updateApplicationState: (S) -> Unit) : UndoAction("Redo", Icons.Filled.Redo, enabled)
    class PushOnUndoStack<S>(val newApplicationState: S)                            : UndoAction("PushOnUndoStack", null, true)
    object Reset                                                                    : UndoAction("Reset", null, true)
}

