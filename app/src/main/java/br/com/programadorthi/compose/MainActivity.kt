package br.com.programadorthi.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.remember
import androidx.ui.animation.animatedFloat
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.layout.LayoutGravity
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Stack
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import br.com.programadorthi.compose.composables.AppBar
import br.com.programadorthi.compose.composables.Background
import br.com.programadorthi.compose.composables.Drawer
import br.com.programadorthi.compose.controllers.DrawerController
import br.com.programadorthi.compose.helpers.LayoutFractionalOffset

val drawerWidth = 125.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = remember {
                DrawerController(animation = animatedFloat(initVal = 0f))
            }

            MaterialTheme {
                Stack(children = {
                    Background()
                    Box(modifier = LayoutGravity.TopStart + LayoutPadding(top = 24.dp)) {
                        AppBar(onMenuItemClick = {
                            controller.open()
                        })
                    }
                    // TODO: should have a clickable in out area to hide drawer
                    // TODO: clickable doesn't support HitTestBehavior
                    Box(
                        modifier = LayoutGravity.CenterEnd + LayoutFractionalOffset(
                            dx = controller.animationProgress,
                            dy = 0f
                        )
                    ) {
                        Drawer(drawerWidth, onClick = { controller.close() })
                    }
                })
            }
        }
    }
}
