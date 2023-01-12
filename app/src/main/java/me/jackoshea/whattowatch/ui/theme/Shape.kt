package me.jackoshea.whattowatch.ui.theme
/**
 * The built in shapes kt file used for the rounding of elements.
 * @author Jack O'Shea
 */
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

// The rounding used on each shape.
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)