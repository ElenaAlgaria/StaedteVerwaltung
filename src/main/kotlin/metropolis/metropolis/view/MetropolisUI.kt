package metropolis.metropolis.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import metropolis.editor.controller.countryEditorController
import metropolis.editor.view.EditorUi
import metropolis.explorer.view.ExplorerUI
import metropolis.xtracted.model.EditorState
import metropolis.xtracted.repository.Identifiable


@Composable
fun <T : Identifiable> ApplicationScope.MetropolisWindow(
    state: MetropolisState<T>
) {
    Window(
        title = state.title, onCloseRequest = ::exitApplication, state = rememberWindowState(
            width = 1200.dp, height = 800.dp, position = WindowPosition(Alignment.Center)
        )
    ) {
        MetropolisUi(state)
    }
}

@Composable
fun <T : Identifiable> MetropolisUi(state: MetropolisState<T>) {
    with(state) {
        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            ExplorerUI(state = activeExplorerController.state,
                    dataProvider = { activeExplorerController.getData(it) },
                    idProvider = { it.id },
                    trigger = { activeExplorerController.triggerAction(it) })


//            Card(elevation = 2.dp, modifier = Modifier.weight(1.0f).fillMaxSize()) {
//                ExplorerUI(state = activeExplorerController.state,
//                    dataProvider = { activeExplorerController.getData(it) },
//                    idProvider = { it.id },
//                    trigger = { activeExplorerController.triggerAction(it) })
//            }
//            Card(elevation = 2.dp, modifier = Modifier.weight(0.4f).fillMaxSize()) {
//
//                EditorUi(state = activeEditorController.state,
//                    undoState = activeEditorController.undoState,
//                    trigger = { activeEditorController.triggerAction(it) })
//            }
        }

    }

}
//is country -> showLoginUi(state.activeController)
//is  -> showWorkReportUi(state.activeController)
// es objekt inne geh für de controller bim  click uf de explorer
