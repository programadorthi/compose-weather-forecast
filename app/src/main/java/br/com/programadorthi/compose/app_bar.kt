package br.com.programadorthi.compose

import androidx.compose.Composable
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


@Composable
fun AppBar() {
    val surfaceColor = Color.Transparent
    val icRefresh = vectorResource(id = R.drawable.ic_baseline_arrow_forward_ios_24)
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
                        Text(
                            text = "Thursday, August 29",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = TextUnit.Sp(16)
                            )
                        )
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
                        icRefresh.defaultWidth,
                        appBarHeight
                    ) + LayoutAlign.Center
                ) {
                    Clickable(onClick = { println(">>>>> i=clicked") }) {
                        Icon(icon = icRefresh, tint = Color.White)
                    }
                }
            }
        )
    }
}