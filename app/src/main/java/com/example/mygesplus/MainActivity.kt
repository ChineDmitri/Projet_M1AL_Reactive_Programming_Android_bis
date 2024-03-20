package com.example.mygesplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.view.CourseItemView
import com.example.mygesplus.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)

            MainScreen(mainViewModel)
        }
    }
}


@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val currentDate by mainViewModel.currentDate.collectAsState()

        Text(
            text = "Aujourd'hui: $currentDate",
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
        val courses = mainViewModel.coursesList.collectAsState(initial = emptyList())
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(courses.value) { index, course ->
                CourseItemView(course)
            }
        }
    }
}
/*@Composable
fun MainScreen(mainViewModel: MainViewModel ) {
    val courses = mainViewModel.coursesList.collectAsState(initial = emptyList())
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(courses.value) { index, course ->
            CourseItemView(course)
        }
    }

}*/


