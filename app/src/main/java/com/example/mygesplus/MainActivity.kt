package com.example.mygesplus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import com.example.mygesplus.model.Course
import com.example.mygesplus.view.CourseItemView

class MainActivity : ComponentActivity() {
    /*private val viewModel: SharedViewModel by viewModels()*/

    private val courses: List<Course> = listOf(
        Course(
            1,
            "Cours 1",
            "01/01/2022",
            "8h00",
            "8h00",
            "Super Android course",
            true,
        ),
        Course(
            2,
            "Cours 2 : Rust", "02/01/2022",
            "12h00",
            "13h00",
            "Super Rustaman course",
            true,
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(courses) { index, cours ->
                    Log.wtf("IDX::CourseItemView", index.toString())
                    CourseItemView(cours) /*{
                        viewModel.onCourseSelected(cours)
                    }*/
                }
            }
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
