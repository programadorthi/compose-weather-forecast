package br.com.programadorthi.compose.controllers

import androidx.animation.AnimatedValue
import androidx.animation.AnimationVector1D
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

data class DrawerController(
    val drawerWidth: Dp,
    val animation: AnimatedValue<Dp, AnimationVector1D>
) {
    private var state = DrawerState.CLOSED

    val isClosed: Boolean
        get() = state == DrawerState.CLOSED

    val isClosing: Boolean
        get() = state == DrawerState.CLOSING

    val isOpen: Boolean
        get() = state == DrawerState.OPEN

    val isOpening: Boolean
        get() = state == DrawerState.OPENING

    val animationProgress: Dp
        get() = animation.value

    fun close() {
        state = DrawerState.CLOSING

        animation.animateTo(targetValue = drawerWidth, onEnd = { _, _ ->
            state = DrawerState.CLOSED
        })
    }

    fun open() {
        state = DrawerState.OPENING

        animation.animateTo(targetValue = 0.dp, onEnd = { _, _ ->
            state = DrawerState.OPEN
        })
    }

    fun toggle() {
        when {
            isClosed -> open()
            isOpen -> close()
        }
    }
}
