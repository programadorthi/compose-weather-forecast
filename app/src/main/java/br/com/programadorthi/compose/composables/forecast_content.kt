package br.com.programadorthi.compose.composables

import android.content.res.Resources
import androidx.animation.TweenBuilder
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.state
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment
import androidx.ui.core.Opacity
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
import br.com.programadorthi.compose.models.DrawerItem
import br.com.programadorthi.compose.models.RadialItem
import br.com.programadorthi.compose.models.forecasts
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


private const val MAX_BOUND = 1f
private const val MIN_BOUND = 0f

private const val FIRST_ITEM_ANGLE = -PI / 3
private const val LAST_ITEM_ANGLE = PI / 3

private val fadeDuration = TweenBuilder<Float>().apply {
    duration = 150
}

private val slideDuration = TweenBuilder<Float>().apply {
    duration = 1500
}

private enum class RadialState {
    CLOSED, FADING_OUT, OPEN, SLIDING_OPEN
}

private val displayMetrics = Resources.getSystem().displayMetrics
private val halfScreenWidth = displayMetrics.widthPixels / 4.6


@Composable
fun ForecastContent(item: DrawerItem) {
    Stack {
        Background()
        Temperature()
        RadialForecastList(item)
    }
}

@Composable
fun RadialForecastList(item: DrawerItem) {
    val slideAnimation = animatedFloat(initVal = MAX_BOUND).apply {
        setBounds(min = MIN_BOUND, max = MAX_BOUND)
    }

    val fadeAnimation = animatedFloat(initVal = MAX_BOUND).apply {
        setBounds(min = MIN_BOUND, max = MAX_BOUND)
    }

    val radialState = state { RadialState.CLOSED }

    val angleDiffPerItem = (LAST_ITEM_ANGLE - FIRST_ITEM_ANGLE) / forecasts.lastIndex

    var currentAngle = FIRST_ITEM_ANGLE

    onCommit(item) {
        radialState.value = RadialState.FADING_OUT
        fadeAnimation.animateTo(targetValue = MAX_BOUND, anim = fadeDuration, onEnd = { _, _ ->
            radialState.value = RadialState.CLOSED
            slideAnimation.snapTo(MIN_BOUND)
            fadeAnimation.snapTo(MIN_BOUND)
            radialState.value = RadialState.SLIDING_OPEN
            slideAnimation.animateTo(
                targetValue = MAX_BOUND,
                anim = slideDuration,
                onEnd = { _, _ ->
                    radialState.value = RadialState.OPEN
                })
        })
    }

    Stack {
        for (forecast in forecasts) {
            val dx = cos(currentAngle) * halfScreenWidth
            val dy = sin(currentAngle) * halfScreenWidth

            Align(alignment = Alignment.CenterStart) {
                Box(modifier = LayoutOffset(dx.dp, dy.dp)) {
                    Opacity(
                        opacity = when (radialState.value) {
                            RadialState.CLOSED -> MIN_BOUND
                            RadialState.FADING_OUT -> MAX_BOUND - fadeAnimation.value
                            else -> MAX_BOUND
                        }
                    ) {
                        RadialForecast(forecast)
                    }
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
