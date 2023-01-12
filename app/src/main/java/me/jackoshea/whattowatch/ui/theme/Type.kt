package me.jackoshea.whattowatch.ui.theme

/**
 * The font type used throughout the project.
 * @author Jack O'Shea
 */
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.jackoshea.whattowatch.R

/**
 * A font family object named poppinsFontFamily which requests fonts from the resource directory.
 */
val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_regular, FontWeight.Normal),

    )
/**
 * Set of Material typography styles to use throughout the project.
 */

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Black,
        fontStyle = FontStyle.Italic,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    h3 = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize =15.sp
    ),
    body1 = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)