package metropolis.explorer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.metropolis.controller.MetropolisAction
import metropolis.xtracted.model.TableState
import metropolis.xtracted.controller.lazyloading.LazyTableAction
import metropolis.xtracted.view.Table

@Composable
fun <T> ApplicationScope.ExplorerWindow(
    state: TableState<T>,
    dataProvider: (Int) -> T,
    idProvider: (T) -> Int,
    trigger: (LazyTableAction) -> Unit,
    tabIndex: Int,
    tabChange: (Int) -> Unit,
    triggerEditor: (Int) -> Unit
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

        TabScreen(
            state,
            dataProvider,
            idProvider,
            trigger,
            tabIndex,
            tabChange,
            triggerEditor)

    }

}

@Composable
fun <T> TabScreen(
    state: TableState<T>,
    dataProvider: (Int) -> T,
    idProvider: (T) -> Int,
    trigger: (LazyTableAction) -> Unit,
    tabIndex: Int,
    tabChange: (Int) -> Unit,
    triggerEditor: (Int) -> Unit
) {

    val tabs = listOf("Countries", "Cities")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex, backgroundColor = Color.White) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = {
                        tabChange(index)
                    },
                    text = {
                        Box {
                            Text(title)
                            if (index == 0) {
                                Badge(modifier = Modifier.padding(80.dp, 0.dp, 0.dp, 0.dp))
                                { Text("200") }
                            } else {
                                Badge(modifier = Modifier.padding(50.dp, 0.dp, 0.dp, 0.dp))
                                { Text("100") }

                            }

                        }
                    }
                )
            }
        }

        when (tabIndex) {
            0 -> ExplorerUI(state, dataProvider, idProvider, trigger, triggerEditor)
            1 -> ExplorerUI(state, dataProvider, idProvider, trigger, triggerEditor)
        }
    }
}

@Composable
fun <T> ExplorerUI(
    state: TableState<T>,
    dataProvider: (Int) -> T,
    idProvider: (T) -> Int,
    trigger: (LazyTableAction) -> Unit,
    triggerEditor: (Int) -> Unit
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
            triggerEditor = triggerEditor)
    }
}


