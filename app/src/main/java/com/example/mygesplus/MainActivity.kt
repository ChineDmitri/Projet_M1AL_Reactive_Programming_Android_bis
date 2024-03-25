package com.example.mygesplus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.unit.sp
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

    LaunchedEffect(Unit) {
        connectivityViewModel.setIsConnectedAfterDelay(3000) // Change isConnected after 3 sec.
    }


    Column(modifier = Modifier.fillMaxWidth()) {


        val courses: State<List<Course>> = mainViewModel.coursesList.collectAsState(
            initial = emptyList()
        )

        // Topbar pour la date
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Aujourd'hui: $currentDate",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(color = Color.White, fontSize = 18.sp)
            )
        }

        // Item Recycler
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                val currentDate by mainViewModel.currentDate.collectAsState()

                Text(
                    text = "$currentDate",
                    modifier = Modifier.padding(16.dp)
                )
                Row(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = { mainViewModel.subtractDay() }) {
                        Text(text = "Prev")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { mainViewModel.addDay() }) {
                        Text(text = "Next")
                    }
                }
            }

            itemsIndexed(courses.value) { index, course ->
                CourseItemView(course)
            }
        }

        ConnectivityStatusBar(isConnected)
    }
}




