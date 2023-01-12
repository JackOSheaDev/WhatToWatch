package me.jackoshea.whattowatch.components

/**
 * BottomNavBar shown at the bottom of the screen of the application always.
 * @author Jack O'Shea
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import me.jackoshea.whattowatch.R
import me.jackoshea.whattowatch.ui.theme.yellow

/**
 * BottomNavBar shown at the bottom of the screen.
 * @param location The current location of the navigator.
 * @param onNavigate A callback function to change screen.
 * @param modifier Optional styling and positioning.
 */
@Composable
fun BottomNavBar(location: String, onNavigate: (String) -> Unit, modifier: Modifier = Modifier) {

    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {

        IconButton(onClick = {
            // Prevents navigating to the same screen again causing overhead.
            if(location!= "search")
            {
                onNavigate("search")
            }

        }) {
            if(location == "search")
            {
                //Shows the current location highlighted.
                Icon(Icons.Rounded.Search, contentDescription ="Search", tint= yellow)
            }
            else
            {
                Icon(Icons.Rounded.Search, contentDescription ="Search", tint= MaterialTheme.colors.onSecondary)
            }


        }

        IconButton(onClick = {
            if(location!= "popular")
            {
                onNavigate("popular")
            }

        }) {
            if(location=="popular")
            {
                Icon(painterResource(id = R.drawable.trending), contentDescription ="Popular",tint= yellow)
            }
            else
            {
                Icon(painterResource(id = R.drawable.trending), contentDescription ="Popular",tint= MaterialTheme.colors.onSecondary)
            }


        }

        IconButton(onClick = {
            if(location!= "liked-movies")
            {
                onNavigate("liked-movies")
            }
        }) {

            if(location=="liked-movies")
            {
                Icon(painterResource(id = R.drawable.favourite_filled), contentDescription ="Liked Movies",tint= yellow)
            }
            else
            {
                Icon(painterResource(id = R.drawable.favourite_filled), contentDescription ="Liked Movies",tint= MaterialTheme.colors.onSecondary)
            }


        }

        IconButton(onClick = {
            if(location!= "bookmarked-movies")
            {
                onNavigate("bookmarked-movies")
            }
        }) {
            if(location=="bookmarked-movies")
            {
                Icon(painter = painterResource(id = R.drawable.bookmark_filled),contentDescription ="Bookmarked Movies",tint= yellow,)
            }
            else
            {
                Icon(painter = painterResource(id = R.drawable.bookmark_filled),contentDescription ="Bookmarked Movies",tint= MaterialTheme.colors.onSecondary)
            }


        }

    }
}