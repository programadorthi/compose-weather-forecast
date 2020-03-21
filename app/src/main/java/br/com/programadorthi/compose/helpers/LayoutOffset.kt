package br.com.programadorthi.compose.helpers

import androidx.ui.core.LayoutDirection
import androidx.ui.core.LayoutModifier
import androidx.ui.core.ModifierScope
import androidx.ui.unit.Dp
import androidx.ui.unit.IntPxPosition
import androidx.ui.unit.IntPxSize

data class LayoutOffset(val x: Dp, val y: Dp) : LayoutModifier {
    override fun ModifierScope.modifyPosition(
        childSize: IntPxSize,
        containerSize: IntPxSize
    ): IntPxPosition = IntPxPosition(
        (if (layoutDirection == LayoutDirection.Ltr) x else -x).toIntPx(),
        y.toIntPx()
    )
}
