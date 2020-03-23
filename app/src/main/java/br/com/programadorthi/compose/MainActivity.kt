package br.com.programadorthi.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.animation.animatedFloat
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.layout.LayoutGravity
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Stack
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import br.com.programadorthi.compose.composables.AppBar
import br.com.programadorthi.compose.composables.Drawer
import br.com.programadorthi.compose.composables.ForecastContent
import br.com.programadorthi.compose.controllers.DrawerController
import br.com.programadorthi.compose.helpers.LayoutFractionalOffset
import br.com.programadorthi.compose.models.drawerItems
import br.com.programadorthi.compose.models.forecasts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drawerItems = drawerItems()
        val forecasts = forecasts()

        setContent {
            val drawerController = remember {
                DrawerController(
                    animation = animatedFloat(initVal = 1f).apply {
                        setBounds(min = 0f, max = 1f)
                    }
                )
            }

            val state = state { drawerItems.first() }

            MaterialTheme {
                Stack(children = {
                    ForecastContent(state.value, forecasts)

                    Box(modifier = LayoutGravity.TopStart + LayoutPadding(top = 24.dp)) {
                        AppBar(drawerController, state)
                    }

                    // TODO: should have a clickable in out area to hide drawer
                    // TODO: clickable doesn't support HitTestBehavior
                    Box(
                        modifier = LayoutGravity.CenterEnd + LayoutFractionalOffset(
                            dx = drawerController.animationProgress,
                            dy = 0f
                        )
                    ) {
                        Drawer(drawerController, drawerItems) { item ->
                            state.value = item
                        }
                    }
                })
            }
        }
    }
}
