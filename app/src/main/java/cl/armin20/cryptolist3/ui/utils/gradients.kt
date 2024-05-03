package cl.armin20.cryptolist3.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import cl.armin20.cryptolist3.ui.theme.cardBgGradientExt
import cl.armin20.cryptolist3.ui.theme.cardBgGradientInt
import cl.armin20.cryptolist3.ui.theme.cardBgLinear1
import cl.armin20.cryptolist3.ui.theme.cardBgLinear2
import cl.armin20.cryptolist3.ui.theme.cardBgLinear3
import cl.armin20.cryptolist3.ui.theme.surfaceBgGradientExt
import cl.armin20.cryptolist3.ui.theme.surfaceBgGradientInt

fun Modifier.surfaceBgRadialGradient(
    colors: List<Color> = listOf(surfaceBgGradientInt, surfaceBgGradientExt),
    radius: Float = 1600f
) = this.then(
    background(
        brush = Brush.radialGradient(
            colors = colors,
            center = Offset(800f, 1200f),
            radius = radius,
            tileMode = TileMode.Clamp
        )
    )
)

fun Modifier.cardBgRadialGradient(
    colors: List<Color> = listOf(cardBgGradientInt, cardBgGradientExt),
    radius: Float = 1600f
) = this.then(
    background(
        brush = Brush.radialGradient(
            colors = colors,
            center = Offset(800f, 1200f),
            radius = radius,
            tileMode = TileMode.Clamp
        )
    )
)

fun Modifier.cardBgLinearGradient(
    colors: List<Color> = listOf(cardBgLinear1, cardBgLinear2, cardBgLinear3),
) = this.then(
    background(
        brush = Brush.linearGradient(
            colors = colors,
            tileMode = TileMode.Clamp,
            start = Offset.Zero,
            end = Offset(0.0f, 700f),
        ),
        shape = RoundedCornerShape(30.dp)
    )
)