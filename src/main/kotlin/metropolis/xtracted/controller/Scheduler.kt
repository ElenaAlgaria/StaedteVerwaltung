package metropolis.xtracted.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Marco Sprenger, Livio Näf
 *
 * Scheduler to run only one task. If a new task is added, the old one is overwritten.
 * If the scheduler is in process, after a short delay the newest task is executes.
 * After execution the scheduler checks if a new task is available, if not the scheduler is paused.
 */
class Scheduler(private val delayInMillis: Long = 50L) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var inProcess = false
    private var task: (() -> Unit)? = null
    private var taskToDo: (() -> Unit)? = null

    private fun process() {
        if (inProcess) return
        if (task == null) return
        inProcess = true
        taskToDo = task
        scope.launch {
            delay(delayInMillis)

            if (taskToDo == task) {
                    taskToDo?.invoke()
            }
        }.invokeOnCompletion {
            inProcess = false
            if (task == taskToDo) task = null
            process()
        }
    }

    fun scheduleTask(task: () -> Unit) {
        this.task = task
        process()
    }
}
