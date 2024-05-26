package metropolis.metropolis.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.metropolis.controller.MetropolisState
import metropolis.editor.view.EditorUi
import metropolis.explorer.view.ExplorerUI
//import metropolis.explorer.view.TabScreen
import metropolis.metropolis.controller.MetropolisAction
import metropolis.metropolis.data.Country


@Composable
fun ApplicationScope.MetropolisWindow(
    state: MetropolisState, trigger: (MetropolisAction) -> Unit
) {
    Window(
        title = state.title, onCloseRequest = ::exitApplication, state = rememberWindowState(
            width = 1200.dp, height = 800.dp, position = WindowPosition(Alignment.Center)
        )
    ) {
        MetropolisUi(state, trigger)
    }
}

@Composable
fun MetropolisUi(state: MetropolisState, trigger: (MetropolisAction) -> Unit) {

    with(state) {
        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {

            Card(elevation = 2.dp, modifier = Modifier.weight(1.0f).fillMaxSize()) {

                ExplorerUI(
                    state = activeCountryExplorerController.state,
                    dataProvider = { activeCountryExplorerController.getData(it) },
                    idProvider = { it.id },
                    trigger = {
                        activeCountryExplorerController.triggerAction(it)
                    },
                    triggerEditor = {
                        if (activeCountryExplorerController != null) {
                            trigger(MetropolisAction.SwitchToCountryEditor(it))
                            trigger(MetropolisAction.SwitchToCityExplorer(it))
                        } else {
                            trigger(MetropolisAction.SwitchToCityEditor(it))
                        }
                    }
                )
            }

//                TabScreen(state = activeExplorerController.state,
//                    dataProvider = { activeExplorerController.getData(it) },
//                    idProvider = { it.id },
//                    trigger = { activeExplorerController.triggerAction(it) },
//                    tabIndex = 0,
//                    tabChange = { 0 == it },
//                    triggerEditor = {
//                        if (activeExplorerController.state.title == "Countries of the World") {
//                            trigger(MetropolisAction.SwitchToCountryEditor(it))
//                        } else {
//                            trigger(MetropolisAction.SwitchToCityEditor(it))
//                        }
//                    })

            Card(elevation = 2.dp, modifier = Modifier.weight(0.4f).fillMaxSize()) {
                EditorUi(state = activeEditorController.state,
                    undoState = activeEditorController.undoState,
                    trigger = { activeEditorController.triggerAction(it) })
            }
        }

    }
}
