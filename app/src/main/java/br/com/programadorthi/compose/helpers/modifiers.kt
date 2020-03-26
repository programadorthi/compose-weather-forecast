package br.com.programadorthi.compose.helpers

import androidx.ui.core.LayoutDirection
import androidx.ui.core.LayoutModifier
import androidx.ui.core.ModifierScope
import androidx.ui.unit.Dp
import androidx.ui.unit.IntPxPosition
import androidx.ui.unit.IntPxSize

// TODO: LayoutOffset already exists in dev07
data class LayoutOffset(val dx: Dp, val dy: Dp) : LayoutModifier {
    override fun ModifierScope.modifyPosition(
        childSize: IntPxSize,
        containerSize: IntPxSize
    ): IntPxPosition = IntPxPosition(
        x = (if (layoutDirection == LayoutDirection.Ltr) dx else -dx).toIntPx(),
        y = dy.toIntPx()
    )
}

data class LayoutFractionalOffset(val dx: Float, val dy: Float) : LayoutModifier {
    override fun ModifierScope.modifyPosition(
        childSize: IntPxSize,
        containerSize: IntPxSize
    ): IntPxPosition = IntPxPosition(
        x = childSize.width * dx * if (layoutDirection == LayoutDirection.Ltr) 1f else -1f,
        y = childSize.height * dy
    )

    companion object {
        val Origin = LayoutFractionalOffset(0f, 0f)
    }
}
