package br.com.programadorthi.compose.models

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow


private val dayFormat = SimpleDateFormat("EEEE", Locale.US)
private val monthFormat = SimpleDateFormat("MMMM", Locale.US)

data class DrawerItem(
    val day: String,
    val dayName: String,
    val monthName: String
) {
    fun withComma() = "$dayName, $monthName $day"
    fun withLineBreak() = "$dayName\n$monthName $day"
}

fun drawerItems(): List<DrawerItem> {
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