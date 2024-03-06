package com.example.mygesplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.model.Course
import com.example.mygesplus.ui.theme.JaunePomme
import com.example.mygesplus.ui.theme.RougePomme
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

    Box(modifier = Modifier
        .background(JaunePomme)
        .fillMaxWidth()
        .fillMaxHeight()
    )

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp,100.dp,0.dp,0.dp)
    )
    {
        itemsIndexed(courses) { index, course ->
            CourseItemView(course)
        }
    }

    Box(modifier = Modifier
        .background(RougePomme)
        .fillMaxWidth()
        .fillMaxHeight(0.1f)
    )
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
