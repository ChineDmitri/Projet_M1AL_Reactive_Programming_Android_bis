package com.example.mygesplus.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mygesplus.model.Course
import java.sql.Timestamp

class MainViewModel: ViewModel() {
    private val _courses: MutableState<List<Course>> = mutableStateOf(
        listOf(
            Course(
                1,
                "Cours 1",
                Timestamp.valueOf("2022-01-01 00:00:00"),
                "8h00",
                "10h00",
                "Super Android course",
                true,
                emptyList()
            ),
            Course(
                2,
                "Cours 2 : Rust",
                Timestamp.valueOf("2022-01-02 00:00:00"),
                "12h00",
                "13h00",
                "Super Rustaman course",
                true,
                emptyList()
            )
        )
    )

    val courses: MutableState<List<Course>> get() = _courses
}