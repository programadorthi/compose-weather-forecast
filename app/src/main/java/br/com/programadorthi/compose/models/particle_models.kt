package br.com.programadorthi.compose.models

import androidx.ui.geometry.Offset
import androidx.ui.graphics.Canvas
import androidx.ui.graphics.Image
import androidx.ui.graphics.Paint
import java.lang.Math.random


private data class Particle(
    private val startXPos: Float,
    private val startYPos: Float,
    private val xPosRange: Float,
    private val yPosRange: Float,
    private val minSpeed: Float,
    private val speedRange: Float,
    private val image: Image
) {
    private var speed = minSpeed + random().toFloat() * speedRange
    var xPos: Float = startXPos + random().toFloat() * xPosRange
        private set

    var yPos: Float = startYPos + random().toFloat() * yPosRange
        private set

    fun updatePhysics(change: Float) {
        yPos += change * speed
    }

    fun onDraw(canvas: Canvas) {
        canvas.drawImage(image, Offset(xPos, yPos), Paint().apply {
            alpha = 0.3f
        })
    }
}

class ParticleSystem(
    private val endYPos: Float,
    private val endXPos: Float,
    private val startYPos: Float,
    private val startXPos: Float,
    private val xPosRange: Float,
    private val yPosRange: Float,
    private val minSpeed: Float,
    private val speedRange: Float,
    private val image: Image,
    numParticles: Int
) {
    private val particles = mutableListOf<Particle>()

    init {
        for (i in 0 until numParticles) {
            val particle = Particle(
                startXPos = startXPos,
                startYPos = startYPos,
                xPosRange = xPosRange,
                yPosRange = yPosRange,
                minSpeed = minSpeed,
                speedRange = speedRange,
                image = image
            )
            particles.add(particle)
        }
    }

    fun doDraw(canvas: Canvas) {
        for (particle in particles) {
            particle.onDraw(canvas)
        }
    }

    fun updatePhysics(altDelta: Float) {
        particles.forEachIndexed { index, particle ->
            particle.updatePhysics(altDelta)

            if (particle.xPos !in startXPos..endXPos || particle.yPos !in startYPos..endYPos) {
                particles[index] = Particle(
                    startXPos = startXPos,
                    startYPos = startYPos,
                    xPosRange = xPosRange,
                    yPosRange = yPosRange,
                    minSpeed = minSpeed,
                    speedRange = speedRange,
                    image = image
                )
            }
        }
    }
}