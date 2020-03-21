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
import androidx.ui.unit.Dp
import androidx.ui.unit.sp
import br.com.programadorthi.compose.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

@Composable
fun Drawer(drawerWidth: Dp) {
    val icRefresh = vectorResource(id = R.drawable.ic_baseline_refresh_24)

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
            Icon(icon = icRefresh, tint = Color.White)
            for (item in drawerItems()) {
                Clickable(onClick = { println(">>>> item: $item") }) {
                    Text(
                        text = "${item.dayName}\n${item.monthName} ${item.day}",
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

private data class DrawerItem(
    val day: String,
    val dayName: String,
    val monthName: String
)

private val dayFormat = SimpleDateFormat("EEEE", Locale.US)
private val monthFormat = SimpleDateFormat("MMMM", Locale.US)

private fun drawerItems(): List<DrawerItem> {
    val items = mutableListOf<DrawerItem>()
    val calendar = Calendar.getInstance()
    var count = 1.0
    do {
        val dayNumber = calendar.get(Calendar.DAY_OF_MONTH)
        val item = DrawerItem(
            day = if (dayNumber < 10) "0$dayNumber" else "$dayNumber",
            dayName = dayFormat.format(
                calendar.time
            ),
            monthName = monthFormat.format(
                calendar.time
            )
        )
        calendar.add(Calendar.DAY_OF_MONTH, count.pow(0).toInt())
        items.add(item)
        count++
    } while (count <= 7)
    return items
}