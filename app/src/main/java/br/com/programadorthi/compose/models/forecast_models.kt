package br.com.programadorthi.compose.models

import androidx.annotation.DrawableRes
import br.com.programadorthi.compose.R
import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("HH:mm", Locale.US)

private fun time(increaseHourIn: Int = 0): String {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.HOUR, increaseHourIn)
    }
    return formatter.format(calendar.time)
}

data class RadialItem(
    @DrawableRes val icon: Int,
    val title: String = "",
    val subtitle: String = "",
    val selected: Boolean = false
)

fun forecasts() = listOf(
    RadialItem(
        icon = R.drawable.ic_baseline_wb_rainy_24,
        title = time(),
        subtitle = "Light Rain",
        selected = true
    ),
    RadialItem(
        icon = R.drawable.ic_baseline_wb_rainy_24,
        title = time(increaseHourIn = 1),
        subtitle = "Light Rain"
    ),
    RadialItem(
        icon = R.drawable.ic_baseline_wb_cloudy_24,
        title = time(increaseHourIn = 2),
        subtitle = "Cloudy"
    ),
    RadialItem(
        icon = R.drawable.ic_baseline_wb_sunny_24,
        title = time(increaseHourIn = 3),
        subtitle = "Sunny"
    ),
    RadialItem(
        icon = R.drawable.ic_baseline_wb_sunny_24,
        title = time(increaseHourIn = 4),
        subtitle = "Sunny"
    )
)