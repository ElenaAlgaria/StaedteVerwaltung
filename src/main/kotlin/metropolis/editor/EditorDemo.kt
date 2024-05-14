package metropolis.editor

import androidx.compose.ui.window.application
import metropolis.hello.controller.HelloController
import metropolis.hello.view.HelloWindow

fun main() {
    val controller = HelloController()

    application {
        HelloWindow(controller.state)
    }

}
