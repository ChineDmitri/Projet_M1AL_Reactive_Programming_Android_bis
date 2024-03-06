package com.example.mygesplus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.mygesplus.view.CourseItemView
import com.example.mygesplus.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    /*private val viewModel: SharedViewModel by viewModels()*/
    private val viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(viewModel.courses) { index, cours ->
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
