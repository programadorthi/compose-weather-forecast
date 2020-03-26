package br.com.programadorthi.compose.helpers

import androidx.animation.*

enum class ParticleStates {
    HIDE, SHOW
}

val easeInOut = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)

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

data class TweenEasing(
    private val begin: Float,
    private val end: Float,
    private val easing: Easing = LinearEasing
) : Easing {
    override fun invoke(fraction: Float): Float =
        begin + (end - begin) * easing.invoke(fraction)
}

data class IntervalEasing(
    private val begin: Float,
    private val end: Float,
    private val easing: Easing = LinearEasing
) : Easing {
    override fun invoke(fraction: Float): Float {
        val localFraction = ((fraction - begin) / (end - begin)).coerceIn(0f, 1f)
        return easing.invoke(localFraction)
    }
}
