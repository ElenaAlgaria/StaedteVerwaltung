package metropolis.editor.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import metropolis.editor.controller.Id
import metropolis.xtracted.controller.editor.EditorAction
import metropolis.xtracted.model.Attribute
import metropolis.xtracted.model.EditorState
import metropolis.xtracted.model.UndoState
import metropolis.xtracted.model.format
import metropolis.xtracted.model.get
import metropolis.xtracted.model.pp
import metropolis.xtracted.view.VSpace
import metropolis.xtracted.view.editor.EditorBar
import metropolis.xtracted.view.editor.Form

@Composable
fun <T> ApplicationScope.EditorWindow(state: EditorState<T>, undoState: UndoState, trigger : (EditorAction) -> Unit) {

    Window(title          = state.title,
        onCloseRequest = ::exitApplication,
        state          = rememberWindowState(width    = 700.dp,
            height   = 900.dp,
            position = WindowPosition(Alignment.Center)

        )) {

        EditorUi(state, undoState, trigger)
    }
}

@Composable
fun <T> EditorUi(state: EditorState<T>, undoState: UndoState, trigger : (EditorAction) -> Unit) {
    Column{
        EditorBar(state, undoState, trigger)

        Header(state)

        Card(modifier = Modifier.padding(10.dp)
            .weight(1.0f)) {
            Form(state   = state,
                trigger = trigger)
        }
    }
}



@Composable
private fun <T> Header(state: EditorState<T>) {
    val name    : Attribute<String>       = state[Id.NAME]

    val huge       = 42.sp
    val little      = 12.sp

    Row(modifier = Modifier.height(IntrinsicSize.Max).padding(10.dp)){
        Column(modifier = Modifier.weight(1.0f)) {
            Text(text     = "${state.title} ",
                fontSize = little,
                fontWeight = FontWeight.Medium,
            )
            Headline(text = name.value.format("??"), fontSize = huge)
            VSpace(10.dp)
        }
    }
}

@Composable
private fun Headline(text: String, fontSize: TextUnit){
    Text(text       = text,
        maxLines   = 1,
        overflow   = TextOverflow.Ellipsis,
        fontSize   = fontSize,
        fontWeight = FontWeight.ExtraLight)
}
