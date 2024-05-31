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
                                            val testMode: Boolean = false
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

    fun triggerAction(action: A) =
        ioScope.launch {
            actionChannel.send(action)
        }

    fun handleNextAction() : Job =
        ioScope.launch {
            val action = actionChannel.receive()

            if (action.enabled) {
                state = executeAction(action)
            }

            if(!testMode){
                handleNextAction()
            }
        }

    abstract fun executeAction(action: A) : S


    fun close() {
        actionChannel.cancel()
        actionChannel.close()
    }

}
