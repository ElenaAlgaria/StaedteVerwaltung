package metropolis.xtracted.view.editor

import java.util.*
import androidx.compose.runtime.Composable
import metropolis.xtracted.controller.editor.EditorAction
import metropolis.xtracted.model.CH
import metropolis.xtracted.model.EditorState
import metropolis.xtracted.model.UndoState
import metropolis.xtracted.view.ActionIconStrip
import metropolis.xtracted.view.AlignLeftRight
import metropolis.xtracted.view.Toolbar

@Composable
fun EditorBar(state: EditorState<*>, undoState: UndoState, trigger : (EditorAction) -> Unit) {
    Toolbar {
        AlignLeftRight{
            ActionIconStrip(trigger,
                            listOf(EditorAction.Save(state.changed && state.valid),
                                   EditorAction.Reload),

                            listOf(EditorAction.Undo(undoState.undoAvailable),
                                   EditorAction.Redo(undoState.redoAvailable))
                           )
            ActionIconStrip(trigger,
                            listOf(EditorAction.SetLocale(CH, state.locale != CH),
                                   EditorAction.SetLocale(Locale.ENGLISH, state.locale != Locale.ENGLISH))
                           )
        }
    }
}
