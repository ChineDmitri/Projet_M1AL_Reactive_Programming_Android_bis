package com.example.mygesplus.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mygesplus.model.Course

class MainViewModel: ViewModel() {
    private val _courses: MutableState<List<Course>> = mutableStateOf(
        listOf(
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
    )

    val courses: MutableState<List<Course>> get() = _courses
}