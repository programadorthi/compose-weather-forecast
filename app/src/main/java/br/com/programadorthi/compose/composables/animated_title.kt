package br.com.programadorthi.compose.composables

import androidx.animation.FastOutSlowInEasing
import androidx.animation.TweenBuilder
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.remember
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Stack
import androidx.ui.text.TextStyle
import androidx.ui.unit.TextUnit
import br.com.programadorthi.compose.helpers.LayoutFractionalOffset
import br.com.programadorthi.compose.models.AppTitle

val TweenBuilder = TweenBuilder<Float>().apply {
    duration = 750
    easing = FastOutSlowInEasing
}

@Composable
fun AnimatedTitle(title: String) {
    val animation = animatedFloat(initVal = 0f).apply {
        setBounds(min = 0f, max = 1f)
    }

    val appTitle = remember { AppTitle() }

    onCommit(title) {
        if (appTitle.bottomText != title) {
            appTitle.topText = title

            animation.animateTo(targetValue = 1f, anim = TweenBuilder, onEnd = { _, _ ->
                appTitle.apply {
                    bottomText = title
                    topText = ""
                }
                animation.snapTo(0f)
            })
        }
    }

    Stack {
        CustomText(
            modifier = LayoutFractionalOffset(0f, animation.value - 1f),
            text = appTitle.topText
        )
        CustomText(
            modifier = LayoutFractionalOffset(0f, animation.value),
            text = appTitle.bottomText
        )
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