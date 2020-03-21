package br.com.programadorthi.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import br.com.programadorthi.compose.helpers.LayoutOffset

val drawerWidth = 125.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Stack(children = {
                    Background()
                    Box(modifier = LayoutGravity.TopStart + LayoutPadding(top = 24.dp)) {
                        AppBar()
                    }
                    Box(modifier = LayoutGravity.CenterEnd + LayoutOffset(
                        0.dp,
                        0.dp
                    )
                    ) {
                        Drawer(
                            drawerWidth
                        )
                    }
                })
            }
        }
    }
}
