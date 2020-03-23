package br.com.programadorthi.compose.helpers

import kotlin.math.absoluteValue

private const val CubicErrorBound: Float = 0.001f

abstract class Curve {
    fun transform(time: Float) = when (time) {
        0f, 1f -> time
        else -> transformInternal(time)
    }

    protected abstract fun transformInternal(time: Float): Float
}

/**
 * Cubic ported from Flutter because CubicBezierEasing is a final class
 * and implements a Lambda Easing instead of an interface
 */
open class Cubic(
    private val a: Float,
    private val b: Float,
    private val c: Float,
    private val d: Float
) : Curve() {

    private fun evaluateCubic(a: Float, b: Float, m: Float): Float {
        return 3 * a * (1 - m) * (1 - m) * m +
                3 * b * (1 - m) * /*    */ m * m +
                /*                      */ m * m * m
    }

    override fun transformInternal(time: Float): Float {
        if (time > 0f && time < 1f) {
            var start = 0.0f
            var end = 1.0f
            while (true) {
                val midpoint = (start + end) / 2
                val estimate = evaluateCubic(a, c, midpoint)
                if ((time - estimate).absoluteValue < CubicErrorBound)
                    return evaluateCubic(b, d, midpoint)
                if (estimate < time)
                    start = midpoint
                else
                    end = midpoint
            }
        } else {
            return time
        }
    }
}

data class Interval(
    private val begin: Float,
    private val end: Float,
    private val curve: Curve = Linear
) : Curve() {
    override fun transformInternal(time: Float): Float =
        when (val localTime = ((time - begin) / (end - begin)).coerceIn(0f, 1f)) {
            0f, 1f -> localTime
            else -> curve.transform(localTime)
        }
}

object Linear : Curve() {
    override fun transformInternal(time: Float): Float = time
}

val easeInOut = Cubic(0.42f, 0f, 0.58f, 1f)