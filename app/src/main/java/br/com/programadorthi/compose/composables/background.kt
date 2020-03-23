package br.com.programadorthi.compose.composables

import android.content.res.Resources
import androidx.compose.Composable
import androidx.ui.core.Clip
import androidx.ui.core.DensityAmbient
import androidx.ui.core.DrawModifier
import androidx.ui.core.toModifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.shape.GenericShape
import androidx.ui.geometry.Offset
import androidx.ui.geometry.Rect
import androidx.ui.graphics.*
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.Stack
import androidx.ui.res.imageResource
import androidx.ui.unit.Density
import androidx.ui.unit.PxSize
import androidx.ui.unit.toRect
import br.com.programadorthi.compose.R
import androidx.ui.foundation.Canvas as Board

val overlayColor = Color(0xFFAA88AA)
val overlayPaint = Paint()
val overlayBorderPaint = Paint().apply {
    color = Color(0x14FFFFFF)
    style = PaintingStyle.stroke
    strokeWidth = 5f
}

data class Circle(
    val radius: Float,
    val alpha: Float
)

fun Canvas.maskCircle(size: PxSize, radius: Float, centerOffset: Offset) {
    val path = Path().apply {
        setFillType(PathFillType.evenOdd)
        addRect(Rect.fromLTWH(0f, 0f, size.width.value, size.height.value))
        addOval(
            Rect.fromCircle(
                center = Offset(0f, (size.height / 2).value) + centerOffset,
                radius = radius
            )
        )
    }
    clipPath(path)
}

@Composable
fun Background() {
    val displayMetrics = Resources.getSystem().displayMetrics
    val screenHeight = displayMetrics.heightPixels
    val clipRadius = displayMetrics.widthPixels / 2.5f
    val marginLeftOffset = Offset(40f, 0f)
    val center = Offset(0f, screenHeight / 2f) + marginLeftOffset

    Stack {
        Image(
            image = imageResource(
                R.drawable.background_blur
            )
        )
        Clip(shape = GenericShape {
            addOval(Rect.fromCircle(center = center, radius = clipRadius))
        }) {
            Image(
                image = imageResource(
                    R.drawable.background
                )
            )
        }
        WhiteCircleDraw(
            centerOffset = marginLeftOffset,
            circles = listOf(
                Circle(
                    radius = clipRadius,
                    alpha = 0.10f
                ),
                Circle(
                    radius = clipRadius + 30f,
                    alpha = 0.20f
                ),
                Circle(
                    radius = clipRadius + 80f,
                    alpha = 0.30f
                ),
                Circle(
                    radius = clipRadius + 200f,
                    alpha = 0.35f
                )
            )
        )
    }
}

@Composable
fun WhiteCircleDraw(centerOffset: Offset = Offset.zero, circles: List<Circle>) {
    Board(modifier = LayoutSize.Fill, onCanvas = {
        save()

        val parentSize = size

        val circleOffset = Offset(0f, parentSize.height.value / 2) + centerOffset

        for (index in 1 until circles.size) {
            val previousCircle = circles[index - 1]

            maskCircle(
                parentSize,
                previousCircle.radius,
                centerOffset
            )

            overlayPaint.color = overlayColor.copy(
                alpha = previousCircle.alpha
            )

            // Fill circle
            drawCircle(
                center = circleOffset,
                radius = circles[index].radius,
                paint = overlayPaint
            )

            // Draw circle border
            drawCircle(
                center = circleOffset,
                radius = previousCircle.radius,
                paint = overlayBorderPaint
            )
        }

        val lastCircle = circles.last()

        // Mask the area of the final circle
        maskCircle(
            parentSize,
            lastCircle.radius,
            centerOffset
        )

        // Draw an overlay that fills the rest of the screen
        overlayPaint.color = overlayColor.copy(
            alpha = lastCircle.alpha
        )
        drawRect(
            rect = Rect.fromLTWH(0f, 0f, parentSize.width.value, parentSize.height.value),
            paint = overlayPaint
        )

        // Draw circle border
        drawCircle(
            center = circleOffset,
            radius = lastCircle.radius,
            paint = overlayBorderPaint
        )

        restore()
    })
}

// TODO: when update to dev07, remove this composable
@Composable
fun Image(
    image: Image,
    tint: Color? = null
) {
    with(DensityAmbient.current) {
        val imageModifier = ImagePainter(image).toModifier(
            scaleFit = ScaleFit.Fit,
            colorFilter = tint?.let { ColorFilter(it, BlendMode.srcIn) }
        )
        Box(LayoutSize(image.width.toDp(), image.height.toDp()) + ClipModifier + imageModifier)
    }
}

private object ClipModifier : DrawModifier {
    override fun draw(density: Density, drawContent: () -> Unit, canvas: Canvas, size: PxSize) {
        canvas.save()
        canvas.clipRect(size.toRect())
        drawContent()
        canvas.restore()
    }
}