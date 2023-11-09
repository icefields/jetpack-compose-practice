package luci.sixsixsix.composeexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.excludeFromSystemGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luci.sixsixsix.composeexercise.ui.theme.ComposeExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExerciseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

// View = Composable
// default is wrapContent
// ctrl + p = show options
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var textStr by rememberSaveable { mutableStateOf("text") }
    var nameStr by rememberSaveable { mutableStateOf("name") }
    var names by remember { mutableStateOf(listOf<String>()) }
//  var textStr = rememberSaveable { mutableStateOf("text") } doing it this way, when getting the value do textStr.value
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(if (textStr.length >=4) 50.dp else 0.dp)
        ) {
            if (textStr.length >= 4) {
                for (ch in textStr.toCharArray()) {
                    Text(text = ch.toString())
                }
            }
        }

        // Recyclable List
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(if (textStr.length >=4) 50.dp else 0.dp)
        ) {
            items(1000) { i ->
                Text(text = i.toString())
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(6.0f)
            //.size(400.dp)
        ) {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
            ) {
                items(1000) { i ->
                    Text(text = i.toString(),
                        Modifier.padding(14.dp))
                }
            }

            TextField(
                value = textStr,
                onValueChange = {
                    textStr = it
                },
                label = { Text("input something") }
            )

            OutlinedTextField(
                value = nameStr,
                onValueChange = {
                    nameStr = it
                },
                label = { Text("input name") }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        names = names + nameStr
                        nameStr = ""
                    }
                ) {
                    Text(text = "btn 1",)
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    Modifier.background(Color.Black)
                )

                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null
                )

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "btn 3",)
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "btn 4",)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "text view in a Box = FrameLayout",
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

                Text(
                    text = "text 2",
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(text = "text 3") // no modifier, will follow the layout contentAlignment
            }

            Text(
                text = "Hello $name!",
                modifier = Modifier
                    // instructions here are sequential. ie. applying background after padding will exclude
                    // the padded frame from the new background
                    .background(Color.Green)
                    .padding(horizontal = 10.dp, vertical = 14.dp)
                    .background(Color.DarkGray)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                color = Color.White,
                fontSize = 30.sp
            )

            Text(
                text = "Hello second view!",
                modifier = Modifier
                    // instructions here are sequential. ie. applying background after padding will exclude
                    // the padded frame from the new background
                    .background(Color.Green)
                    .padding(horizontal = 10.dp, vertical = 14.dp)
                    .background(Color.DarkGray)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                color = Color.White,
                fontSize = 30.sp
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(4.0f)
                .height(600.dp)
                .background(Color.Blue),
        ) {
            items(names) { currentName ->
                Text(text = currentName)
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeExerciseTheme {
        Greeting("Android")
    }
}