package br.com.programadorthi.compose.controllers

import androidx.animation.AnimatedFloat

data class DrawerController(
    val animation: AnimatedFloat
) {
    val animationProgress: Float
        get() = animation.value

    fun close() {
        animation.animateTo(targetValue = 1f)
    }

    fun open() {
        animation.animateTo(targetValue = 0f)
    }
}
