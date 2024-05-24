package metropolis.metropolis.controller

import androidx.compose.ui.graphics.vector.ImageVector
import metropolis.xtracted.controller.Action

sealed class MetropolisAction(
    override val name: String,
    override val icon: ImageVector?,
    override val enabled: Boolean) : Action {

    class SwitchToCountryEditor(val id: Int)    : MetropolisAction("Country Editor", null, true)
    class SwitchToCityEditor(val id: Int)    : MetropolisAction("City Editor", null, true)
}
