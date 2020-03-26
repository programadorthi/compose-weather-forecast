package br.com.programadorthi.compose.helpers

import androidx.animation.*

enum class ParticleStates {
    HIDE, SHOW
}

val ParticlesProgress = FloatPropKey()

val ParticlesAnimation = transitionDefinition {
    state(ParticleStates.HIDE) {
        this[ParticlesProgress] = 0f
    }

    state(ParticleStates.SHOW) {
        this[ParticlesProgress] = 1f
    }

    transition(fromState = ParticleStates.HIDE, toState = ParticleStates.SHOW) {
        ParticlesProgress using repeatable<Float> {
            iterations = Infinite

            animation = tween {
                easing = FastOutLinearInEasing
                duration = 300
            }
        }
    }
}

interface Animation {
    val value: Float
}

abstract class Animatable {
    fun animate(parent: Animation): Animation = AnimatedEvaluation(parent, this)
    fun evaluate(animation: Animation) = transform(animation.value)
    abstract fun transform(time: Float): Float
}

data class CurvedAnimation(
    private val parent: AnimatedFloat,
    private val curve: Curve?
) : Animation {
    override val value: Float
        get() {
            val time = parent.value
            if (curve != null) {
                return when (time == 0f || time == 1f) {
                    true -> time
                    false -> curve.transform(time)
                }
            }
            return time
        }
}

/**
 * A ported version from Flutter. The current Tween animation is private.
 * Maybe in the future the Android team turns it public
 */
data class Tween(
    val begin: Float,
    val end: Float
) : Animatable() {
    override fun transform(time: Float) = when (time) {
        0f -> begin
        1f -> end
        else -> lerp(time)
    }

    private fun lerp(time: Float) = begin + (end - begin) * time
}

private data class AnimatedEvaluation(
    private val parent: Animation,
    private val animatable: Animatable
) : Animation {

    override val value: Float
        get() = animatable.evaluate(parent)
}