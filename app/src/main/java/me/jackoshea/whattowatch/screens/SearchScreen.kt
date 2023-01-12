package me.jackoshea.whattowatch.screens
/**
 *  Search screen to search for movies.
 */
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.jackoshea.whattowatch.model.MovieViewModel

/**
 * Search screen allows searching for movies using a text input.
 * @param movieViewModel View model to call function.
 * @param navigate A function to call to navigate to the searched results.
 * @param modifier Optional modifier for styling.
 */
@Composable
fun SearchScreen(movieViewModel: MovieViewModel, navigate: () -> Unit, modifier: Modifier = Modifier)
{
    val context = LocalContext.current
    var textValue by remember { mutableStateOf( "") }
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column{
            Text(text = "Welcome to What To Watch", style = MaterialTheme.typography.h1,color= MaterialTheme.colors.onSecondary, textAlign = TextAlign.Center)
            Text(text= "What would you like to search for ?",style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onSecondary, modifier = Modifier.align(
                Alignment.CenterHorizontally
            ))

            // Text field which lets the user type in what they wish to view.
            TextField(singleLine = true, modifier = modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally), colors = TextFieldDefaults.textFieldColors(textColor = Color.White), value = textValue, onValueChange = {textValue = it},
                // ImeAction which allows search on enter key.
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if(textValue.trim() != "")
                        {
                            Log.d("JACK",textValue)
                            movieViewModel.setQuery(textValue.trim())
                            navigate()

                        }
                        else{
                            // Shows a toast if the entry is empty.
                            Toast.makeText(context, "Please enter a query !", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            )
            // Button alternative to the enter key that can be pressed to search.
            Button(modifier = modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally),onClick = {
                if(textValue.trim() != "")
                {
                    Log.d("JACK",textValue)
                    movieViewModel.setQuery(textValue.trim())
                    navigate()

                }
                else{
                    // If there is no entry show the user they need to enter a query.
                    Toast.makeText(context, "Please enter a query !", Toast.LENGTH_SHORT).show()
                }

            }) {
                Text(text = "Search !")
            }
        }


    }
}