package metropolis.xtracted.controller.undo

import metropolis.xtracted.controller.ControllerBase
import metropolis.xtracted.controller.Scheduler
import metropolis.xtracted.model.UndoState

class UndoController<S>(private var debounceStart: S, delay : Long = 200L)
    : ControllerBase<UndoState, UndoAction>(initialState = UndoState(undoAvailable = false,
                                                                     redoAvailable = false)) {

    private val undoStack = ArrayDeque<Snapshot<S>>()
    private val redoStack = ArrayDeque<Snapshot<S>>()

    private val undoStackScheduler = Scheduler(delay)

    override fun executeAction(action: UndoAction) : UndoState =
        when(action){
            is UndoAction.Undo<*>            -> undo(action.updateApplicationState as (S) -> Unit)
            is UndoAction.Redo<*>            -> redo(action.updateApplicationState as (S) -> Unit)
            is UndoAction.PushOnUndoStack<*> -> schedulePushOnUndoStack(action.newApplicationState as S)
            is UndoAction.Reset              -> reset()
        }

    private fun schedulePushOnUndoStack(newState: S): UndoState {
        undoStackScheduler.scheduleTask {

            undoStack.push(Snapshot(debounceStart, newState))
            redoStack.clear()

            state = state.copy(undoAvailable = true,
                               redoAvailable = false)
            debounceStart = newState
        }

        return state
    }

    private fun undo(updateApplicationState:  (S) -> Unit): UndoState =
        if (state.undoAvailable) {
            val snapshot = undoStack.pop()
            redoStack.push(snapshot)
            updateApplicationState(snapshot.beforeAction)
            debounceStart = snapshot.beforeAction
            state.copy(redoAvailable = true,
                       undoAvailable = undoStack.isNotEmpty())
        } else {
            state
        }

    private fun redo(updateApplicationState: (S) -> Unit): UndoState =
        if (state.redoAvailable) {
            val snapshot = redoStack.pop()
            undoStack.push(snapshot)
            updateApplicationState(snapshot.afterAction)
            debounceStart = snapshot.afterAction
            state.copy(redoAvailable = redoStack.isNotEmpty(),
                           undoAvailable = true)
        } else {
            state
        }

    private fun reset(): UndoState = UndoState(undoAvailable = false,
                                               redoAvailable = false)


    private fun <S> ArrayDeque<S>.pop() : S = removeFirst()
    private fun <S> ArrayDeque<S>.push(element: S) = addFirst(element)
}


private data class Snapshot<S>(val beforeAction: S,
                               val afterAction:  S)
