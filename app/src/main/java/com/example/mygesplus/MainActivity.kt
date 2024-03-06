package com.example.mygesplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.model.Course
import com.example.mygesplus.view.CourseItemView
import com.example.mygesplus.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()

            MainScreen(viewModel.courses.value)
        }
    }
}

@Composable
fun MainScreen(courses: List<Course>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(courses) { index, course ->
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
