package drawbox.canvas.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import io.github.markyav.drawbox.controller.DrawBoxBackground
import io.github.markyav.drawbox.controller.DrawBoxConnectionState
import io.github.markyav.drawbox.model.PathWrapper
import kotlinx.coroutines.flow.MutableStateFlow

class DrawController2 {

    private var state: MutableStateFlow<DrawBoxConnectionState> = MutableStateFlow(DrawBoxConnectionState.Disconnected)

    /** A stateful list of [Path] that is drawn on the [Canvas]. */
    private val drawnPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    private val activeDrawingPath: MutableStateFlow<List<Offset>?> = MutableStateFlow(null)

    /** A stateful list of [Path] that was drawn on the [Canvas] but user retracted his action. */
    private val canceledPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    val openedImage: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)

    /** An [canvasOpacity] of the [Canvas] in the [DrawBox] */
    var canvasOpacity: MutableStateFlow<Float> = MutableStateFlow(1f)

    /** An [opacity] of the stroke */
    var opacity: MutableStateFlow<Float> = MutableStateFlow(1f)

    /** A [strokeWidth] of the stroke */
    var strokeWidth: MutableStateFlow<Float> = MutableStateFlow(10f)

    /** A [color] of the stroke */
    var color: MutableStateFlow<Color> = MutableStateFlow(Color.Red)

    /** A [background] of the background of DrawBox */
    var background: MutableStateFlow<DrawBoxBackground> = MutableStateFlow(DrawBoxBackground.NoBackground)
}