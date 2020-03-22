package br.com.programadorthi.compose.composables

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.core.Clip
import androidx.ui.core.Text
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.contentColorFor
import androidx.ui.material.surface.Surface
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import br.com.programadorthi.compose.R
import br.com.programadorthi.compose.controllers.DrawerController
import br.com.programadorthi.compose.models.DrawerItem


@Composable
fun AppBar(
    drawerController: DrawerController,
    state: State<DrawerItem>
) {
    val surfaceColor = Color.Transparent
    val icDrawer = vectorResource(id = R.drawable.ic_baseline_arrow_forward_ios_24)
    val appBarHeight = 60.dp

    Surface(
        color = surfaceColor,
        contentColor = contentColorFor(color = surfaceColor),
        elevation = 0.dp,
        shape = RectangleShape
    ) {
        Row(
            LayoutWidth.Fill + LayoutPadding(
                start = 16.dp,
                end = 16.dp
            ) + LayoutHeight(appBarHeight),
            arrangement = Arrangement.SpaceBetween,
            children = {
                Box {
                    Column {
                        Box {
                            Clip(shape = RectangleShape) {
                                AnimatedTitle(title = state.value.withComma())
                            }
                        }
                        Text(
                            text = "Sacramento",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = TextUnit.Sp(30)
                            )
                        )
                    }
                }
                Box(
                    modifier = LayoutSize(
                        icDrawer.defaultWidth,
                        appBarHeight
                    ) + LayoutAlign.Center
                ) {
                    Clickable(onClick = {
                        drawerController.open()
                    }) {
                        Icon(icon = icDrawer, tint = Color.White)
                    }
                }
            }
        )
    }
}