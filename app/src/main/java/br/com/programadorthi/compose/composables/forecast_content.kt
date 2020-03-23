package br.com.programadorthi.compose.composables

import android.content.res.Resources
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.foundation.Border
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Icon
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import br.com.programadorthi.compose.helpers.LayoutFractionalOffset
import br.com.programadorthi.compose.helpers.LayoutOffset
import br.com.programadorthi.compose.models.RadialItem
import br.com.programadorthi.compose.models.forecasts
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ForecastContent() {
    Stack {
        Background()
        Temperature()
        RadialForecastList()
    }
}

@Composable
fun RadialForecastList() {
    val displayMetrics = Resources.getSystem().displayMetrics
    val halfScreenWidth = displayMetrics.widthPixels / 4.6

    val firstItemAngle = -PI / 3
    val lastItemAngle = PI / 3
    val angleDiffPerItem = (lastItemAngle - firstItemAngle) / forecasts.lastIndex

    var currentAngle = firstItemAngle

    Stack {
        for (item in forecasts) {
            val dx = cos(currentAngle) * halfScreenWidth
            val dy = sin(currentAngle) * halfScreenWidth

            Align(alignment = Alignment.CenterStart) {
                Box(modifier = LayoutOffset(dx.dp, dy.dp)) {
                    RadialForecast(item)
                }
            }

            currentAngle += angleDiffPerItem
        }
    }
}

@Composable
fun RadialForecast(radialItem: RadialItem) {
    Row {
        Box(
            modifier = LayoutSize(60.dp),
            backgroundColor = if (radialItem.selected) Color.White else Color.Transparent,
            border = if (radialItem.selected) null else Border(size = 2.dp, color = Color.White),
            gravity = ContentGravity.Center,
            shape = CircleShape
        ) {
            Icon(
                icon = vectorResource(id = radialItem.icon),
                tint = if (radialItem.selected) Color(0xFF6688CC) else Color.White
            )
        }
        Column(modifier = LayoutPadding(start = 8.dp)) {
            Text(
                text = radialItem.title,
                style = TextStyle(color = Color.White, fontSize = 18.sp)
            )
            Text(
                text = radialItem.subtitle,
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun Temperature() {
    Align(alignment = Alignment.CenterStart) {
        Text(
            modifier = LayoutFractionalOffset(0.05f, 1f),
            text = "68º",
            style = TextStyle(color = Color.White, fontSize = 80.sp)
        )
    }
}
