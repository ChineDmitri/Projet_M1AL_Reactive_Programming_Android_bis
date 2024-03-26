package com.example.mygesplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygesplus.view.AddCourseView
import com.example.mygesplus.viewmodel.AddCourseViewModel

class AddCourseActivity : ComponentActivity() {

    private val viewModel: AddCourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val addCourseViewModel: AddCourseViewModel = viewModel(factory = AddCourseViewModel.factory)

            AddCourseView(addCourseViewModel = addCourseViewModel)
        }
    }
}
