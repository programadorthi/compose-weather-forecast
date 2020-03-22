package br.com.programadorthi.compose.composables

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Icon
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import br.com.programadorthi.compose.R
import br.com.programadorthi.compose.controllers.DrawerController
import br.com.programadorthi.compose.models.DrawerItem

private val drawerWidth = 125.dp

@Composable
fun Drawer(
    controller: DrawerController,
    drawerItems: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    Box(
        modifier = LayoutWidth(drawerWidth) + LayoutHeight.Fill,
        backgroundColor = Color(0xAA234060),
        gravity = ContentGravity.Center,
        shape = RectangleShape
    ) {
        FlowColumn(
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            crossAxisSpacing = drawerWidth,
            mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
            mainAxisSize = SizeMode.Expand
        ) {
            // TODO: icon doesn't have properties to resize the icon
            Icon(icon = vectorResource(id = R.drawable.ic_baseline_refresh_24), tint = Color.White)
            for (item in drawerItems) {
                Clickable(onClick = {
                    onItemClick(item)
                    controller.close()
                }) {
                    Text(
                        text = item.withLineBreak(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}
