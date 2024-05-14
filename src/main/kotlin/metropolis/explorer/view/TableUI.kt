package metropolis.explorer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.explorer.data.Country
import metropolis.xtracted.model.TableState
import metropolis.xtracted.controller.lazyloading.LazyTableAction
import metropolis.xtracted.view.Table



@Composable
fun ApplicationScope.ExplorerWindow(state       : TableState<Country>,
                                           dataProvider: (Int) -> Country,
                                           idProvider  : (Country) -> Int,
                                           trigger     : (LazyTableAction) -> Unit) {
    Window(title          = state.title,
           onCloseRequest = ::exitApplication,
           state          = rememberWindowState(width    = 1200.dp,
                                                height   = 500.dp,
                                                position = WindowPosition(Alignment.Center))) {

        TabScreen(state, dataProvider, idProvider, trigger)
    }
}

@Composable
fun TabScreen(state: TableState<Country>, dataProvider: (Int) -> Country, idProvider: (Country) -> Int, trigger: (LazyTableAction) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Countries", "Cities")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex, backgroundColor = Color.White) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> CountryExplorerUI(state, dataProvider, idProvider, trigger)
            1 -> (null)
        }
    }
}

@Composable
fun CountryExplorerUI(state       : TableState<Country>,
                      dataProvider: (Int) -> Country,
                      idProvider  : (Country) -> Int,
                      trigger     : (LazyTableAction) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()
                              .background(Color(0xFFEEEEEE))
                              .padding(10.dp)) {
        Table(tableState   = state,
              itemProvider = dataProvider,
              idProvider   = idProvider,
              trigger      = trigger,
              modifier     = Modifier.weight(1.0f))
    }
}


