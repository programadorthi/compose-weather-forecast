package br.com.programadorthi.compose.composables

import android.content.res.Resources
import androidx.compose.Composable
import androidx.ui.animation.Transition
import androidx.ui.foundation.Canvas
import androidx.ui.layout.LayoutSize
import androidx.ui.res.imageResource
import br.com.programadorthi.compose.R
import br.com.programadorthi.compose.helpers.ParticleStates
import br.com.programadorthi.compose.helpers.ParticlesAnimation
import br.com.programadorthi.compose.helpers.ParticlesProgress
import br.com.programadorthi.compose.models.ParticleSystem

@Composable
fun RainParticles() {
    val displayMetrics = Resources.getSystem().displayMetrics
    val screenHeight = displayMetrics.heightPixels
    val screenWidth = displayMetrics.widthPixels
    val particleSystem = ParticleSystem(
        startXPos = -screenWidth * 0.1f,
        startYPos = -screenHeight * 0.6f,
        endXPos = 0f,
        endYPos = screenHeight.toFloat(),
        xPosRange = screenWidth.toFloat(),
        yPosRange = screenWidth * 0.1f,
        minSpeed = 100f,
        speedRange = 100f,
        image = imageResource(id = R.drawable.rain),
        numParticles = 6
    )
    Transition(
        definition = ParticlesAnimation,
        initState = ParticleStates.HIDE,
        toState = ParticleStates.SHOW
    ) { state ->
        Canvas(modifier = LayoutSize.Fill, onCanvas = {
            save()
            rotate(degrees = 8f)
            particleSystem.doDraw(this)
            particleSystem.updatePhysics(1f - state[ParticlesProgress])
            restore()
        })
    }

}