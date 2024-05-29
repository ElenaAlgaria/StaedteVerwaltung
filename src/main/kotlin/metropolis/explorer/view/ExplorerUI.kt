package metropolis.explorer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.xtracted.model.TableState
import metropolis.xtracted.controller.lazyloading.LazyTableAction
import metropolis.xtracted.repository.Identifiable
import metropolis.xtracted.view.Table

@Composable
fun <T> ApplicationScope.ExplorerWindow(
    state: TableState<T>,
    dataProvider: (Int) -> T,
    idProvider: (T) -> Int,
    trigger: (LazyTableAction) -> Unit,
    triggerEditor: (Int) -> Unit,
    triggerExplorer: (T) -> Unit,
    triggerCreate: () -> Unit
) {
    Window(
        title = state.title,
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            width = 1200.dp,
            height = 500.dp,
            position = WindowPosition(Alignment.Center)
        )
    ) {
        ExplorerUI(state, dataProvider, idProvider, trigger, triggerEditor, triggerExplorer, triggerCreate)
    }

}
@Composable
fun <T> ExplorerUI(
    state: TableState<T>,
    dataProvider: (Int) -> T,
    idProvider: (T) -> Int,
    trigger: (LazyTableAction) -> Unit,
    triggerEditor: (Int) -> Unit,
    triggerExplorer: (T) -> Unit,
    triggerCreate: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(10.dp)
    ) {
        Table(
            tableState = state,
            itemProvider = dataProvider,
            idProvider = idProvider,
            trigger = trigger,
            modifier = Modifier.weight(1.0f),
            triggerEditor = triggerEditor,
            triggerExplorer = triggerExplorer,
            triggerCreate = triggerCreate)
    }
}
