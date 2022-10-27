package customCanvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

data class PathProperties(val Angle: Float, val length: Float, val startPoint: Pair<Float, Float>, val endPoint: Pair<Float, Float>)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun customCanvas(){
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
//    var currentPath by remember { mutableStateOf(Path()) }
//    val paths = remember { mutableStateListOf<Path>() }
    val randomAngle = listOf(45f, -45f)

    val paths = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var currentPath by remember { mutableStateOf(Path()) }
    var show by remember { mutableStateOf(false) }
    val lineLength = 30f


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown().also {

                            currentPosition = it.position
                            previousPosition = currentPosition
                            currentPath.moveTo(currentPosition.x, currentPosition.y)
                            val angle = randomAngle.random()

                            val startPoint = Pair(currentPosition.x, currentPosition.y)
                            val endPoint = getPointByAngle(lineLength, angle, startPoint)
                            currentPath.lineTo(endPoint.first, endPoint.second)
                            paths.add(Pair(currentPath, PathProperties(angle, 30f, startPoint, endPoint)))
                        }
                    }
                }
            }
            .onPointerEvent(PointerEventType.Move) {
                val position = it.changes.first().position
                show = (position.x in 90f..110f)  && position.y in 90f..110f
            }
    ){
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            paths.forEach { it: Pair<Path, PathProperties> ->

                drawPath(
                    color = Color.Black,
                    path = it.first,
                    style = Stroke(
                        width = 3f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    )

                )

                drawCircle(
                    color = Color.Cyan,
                    radius = 8f,
                    center = Offset(it.second.startPoint.first, it.second.startPoint.second),
                )
                drawCircle(
                    color = Color.Cyan,
                    radius = 8f,
                    center = Offset(it.second.endPoint.first, it.second.endPoint.second),
                )

            }
        }
    }
}

//calculate the end point x and y coordinate by cos() and sin()
fun getPointByAngle(length: Float, angle: Float, startPoint: Pair<Float, Float>): Pair<Float, Float> {
    return Pair(startPoint.first + length * cos(angle), startPoint.second + length * sin(angle))
}


