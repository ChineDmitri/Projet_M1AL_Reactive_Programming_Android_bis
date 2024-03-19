package com.example.mygesplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
fun MainScreen(mainViewModel: MainViewModel ) {
    val courses = mainViewModel.coursesList.collectAsState(initial = emptyList())
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        /*item(courses.value) {
            CourseItemView(course)
        }*/
        itemsIndexed(courses.value) { index, course ->
            CourseItemView(course)
        }
    }
}

/*class SharedViewModel : ViewModel() {
    private val _courseEvent = MutableSharedFlow<CourseEvent>()
    val courseEvent: SharedFlow<CourseEvent> get() = _courseEvent

    fun onCourseSelected(course: Course) {
        viewModelScope.launch {
            _courseEvent.emit(CourseEvent.CourseSelected(course))
        }
    }
}*/
