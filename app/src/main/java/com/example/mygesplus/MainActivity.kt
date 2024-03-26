package com.example.mygesplus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.model.Course
import com.example.mygesplus.view.ConnectivityStatusBar
import com.example.mygesplus.view.CourseItemView
import com.example.mygesplus.viewmodel.ConnectivityViewModel
import com.example.mygesplus.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    lateinit var connectivityViewModel: ConnectivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityViewModel = ViewModelProvider(
            this,
            ConnectivityViewModel.provideFactory(this)
        ).get(ConnectivityViewModel::class.java)

        setContent {
            val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)

            MainScreen(mainViewModel, connectivityViewModel = connectivityViewModel)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    connectivityViewModel: ConnectivityViewModel = viewModel()
) {
    val isConnected by connectivityViewModel.isConnected.collectAsState()
    val context: Context = LocalContext.current

    LaunchedEffect(Unit) {
        connectivityViewModel.setIsConnectedAfterDelay(3000) // Change isConnected after 3 sec.
    }

    val currentDate by mainViewModel.currentDate.collectAsState()

    val courses: State<List<Course>> = mainViewModel.coursesList.collectAsState(
        initial = emptyList()
    )

    // Topbar pour la date
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(Color(249, 25, 21, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        )
    {
        Text(
            text = "Aujourd'hui: $currentDate",
            modifier = Modifier.padding(16.dp),
            style = TextStyle(color = Color.White, fontSize = 26.sp)
        )
    }

    ConnectivityStatusBar(isConnected)

    val scrollingState = rememberLazyListState()
    val buttonsVisible = remember { mutableStateOf(true) }

    Box() {

        // Item Recycler
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { buttonsVisible.value = !buttonsVisible.value },
                    onLongPress = { buttonsVisible.value = !buttonsVisible.value },
                )
            }) {
            itemsIndexed(courses.value) { index, course ->
                CourseItemView(course)
            }
        }

        // Row box bouttons de navigation
        if (buttonsVisible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .alpha(if (buttonsVisible.value) 1f else 0f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {

                Button(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    onClick = { mainViewModel.subtractDay() },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(146, 250, 61, 255),
                        contentColor = Color.White
                    )
                )
                {
                    Text(
                        text = "<", modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 7.dp),
                        style = TextStyle(color = Color.White, fontSize = 50.sp)
                    )
                }

                Button(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    onClick = { navigateToAddCourseView(context)},
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(146, 250, 61, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "+",
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 7.dp),
                        style = TextStyle(color = Color.White, fontSize = 50.sp)
                    )
                }

                Button(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    onClick = { mainViewModel.addDay() },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(146, 250, 61, 255),
                        contentColor = Color.White
                    )
                )
                {
                    Text(
                        text = ">",
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 7.dp),
                        style = TextStyle(color = Color.White, fontSize = 50.sp)
                    )
                }
            }
        }


    }
}

private fun navigateToAddCourseView(context: Context ){
    val intent = Intent(context, AddCourseActivity::class.java)
    context.startActivity(intent)
}



