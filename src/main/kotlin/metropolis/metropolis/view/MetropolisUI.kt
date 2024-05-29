package metropolis.metropolis.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.metropolis.controller.MetropolisState
import metropolis.editor.view.EditorUi
import metropolis.explorer.view.ExplorerUI
//import metropolis.explorer.view.TabScreen
import metropolis.metropolis.controller.MetropolisAction
import metropolis.xtracted.controller.lazyloading.LazyTableAction


@Composable
fun ApplicationScope.MetropolisWindow(
    state: MetropolisState, trigger: (MetropolisAction) -> Unit
) {
    Window(
        title = state.title, onCloseRequest = ::exitApplication, state = rememberWindowState(
            width = 1500.dp, height = 800.dp, position = WindowPosition(Alignment.Center)
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
            var name = ""
            Column(modifier = Modifier.weight(0.5f)) {

                Card(elevation = 2.dp, modifier = Modifier.weight(0.2f).fillMaxSize()) {

                    ExplorerUI(
                        state = activeCountryExplorerController.state,
                        dataProvider = { activeCountryExplorerController.getData(it) },
                        idProvider = { it.id },
                        trigger = {
                            activeCountryExplorerController.triggerAction(it)
                        },
                        triggerEditor = {
                            trigger(MetropolisAction.SwitchToCountryEditor(it))
                        },
                        triggerExplorer = {
                            trigger(
                                MetropolisAction.SwitchToCityExplorer(
                                    it.capital ?: "No Capital",
                                    it.isoAlpha2
                                )
                            )
                        }

                    )
                }

                Card(elevation = 2.dp, modifier = Modifier.weight(0.2f).fillMaxSize()) {
                    ExplorerUI(
                        state = activeCityExplorerController.state,
                        dataProvider = { activeCityExplorerController.getData(it) },
                        idProvider = { it.id },
                        trigger = {
                            activeCityExplorerController.triggerAction(it)
                        },
                        triggerEditor = {
                            trigger(MetropolisAction.SwitchToCityEditor(it))
                        },
                        triggerExplorer = {
                            trigger(MetropolisAction.SwitchToCountryExplorer(it.countryCode))
                        }

                    )
                }

            }

            Card(elevation = 2.dp, modifier = Modifier.weight(0.2f).fillMaxSize()) {
                EditorUi(state = activeCountryEditorController.state,
                    undoState = activeCountryEditorController.undoState,
                    trigger = { activeCountryEditorController.triggerAction(it) })
            }

            Card(elevation = 2.dp, modifier = Modifier.weight(0.2f).fillMaxSize()) {
                EditorUi(state = activeCityEditorController.state,
                    undoState = activeCityEditorController.undoState,
                    trigger = { activeCityEditorController.triggerAction(it) })
            }
        }

    }
}
