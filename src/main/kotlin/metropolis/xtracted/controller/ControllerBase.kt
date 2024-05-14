package metropolis.xtracted.controller

import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue

val ch = Locale("de", "CH")

abstract class ControllerBase<S, A: Action>(initialState: S,
                                            val testMode: Boolean = false  //nur für Unit-Tests !!
                                           ){

    var state by mutableStateOf(initialState, policy = neverEqualPolicy())
        protected set

    protected val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val actionChannel = Channel<A>(Channel.UNLIMITED)

    init {
        if(!testMode){
            handleNextAction()
        }
    }

    /**
     * Die Action wird nur in den Channel gestellt. Damit wird ein UI-Freeze sicher vermieden.
     *
     * Durch den Channel lässt sich die Reihenfolgetreue der Actions sicherstellen.
     */
    fun triggerAction(action: A) =
        ioScope.launch {
            actionChannel.send(action)
        }

    /**
     * Jede Action wird in einer eigenen Coroutine ausgeführt.
     *
     * Erst wenn die Action komplett ausgeführt ist und der State neu gesetzt ist, wird die nächste Action ausgeführt.
     */
    fun handleNextAction() : Job =
        ioScope.launch {
            val action = actionChannel.receive()  // warten auf die nächste Action im Channel

            if (action.enabled) {
                state = executeAction(action)  // der State erhält einen neuen Wert. Dadurch wird ein Recompose ausgelöst.
            }

            if(!testMode){  //im TestMode muss das Ausführen der nächsten Action vom TestCase aus gesteuert werden
                handleNextAction()
            }
        }

    /**
     * führt die Action aus und liefert den neuen State zurück
     */
    abstract fun executeAction(action: A) : S

    /**
     * Wenn der Controller nicht mehr benötigt wird, muss der Channel geschlossen werden.
     */
    fun close() {
        actionChannel.cancel()
        actionChannel.close()
    }

}
