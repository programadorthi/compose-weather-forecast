package br.com.programadorthi.compose.controllers

import androidx.animation.AnimatedFloat

data class DrawerController(
    val animation: AnimatedFloat
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

    val animationProgress: Float
        get() = animation.value

    fun close() {
        state = DrawerState.CLOSING

        animation.animateTo(targetValue = 0f, onEnd = { _, _ ->
            state = DrawerState.CLOSED
        })
    }

    fun open() {
        state = DrawerState.OPENING

        animation.animateTo(targetValue = 1f, onEnd = { _, _ ->
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
