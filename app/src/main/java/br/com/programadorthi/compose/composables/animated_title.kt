package br.com.programadorthi.compose.composables

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Stack
import androidx.ui.text.TextStyle
import androidx.ui.unit.TextUnit
import br.com.programadorthi.compose.helpers.LayoutFractionalOffset
import br.com.programadorthi.compose.models.DrawerItem

@Composable
fun AnimatedTitle(item: DrawerItem) {
    Stack {
        CustomText(text = "${item.dayName}, ${item.monthName} ${item.day}")
        CustomText(text = "")
    }
}

@Composable
private fun CustomText(
    modifier: LayoutFractionalOffset = LayoutFractionalOffset.Zero,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = Color.White,
            fontSize = TextUnit.Sp(16)
        )
    )
}