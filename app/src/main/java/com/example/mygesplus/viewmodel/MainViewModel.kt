package com.example.mygesplus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mygesplus.model.Course

class MainViewModel: ViewModel() {
    public val courses: List<Course> = listOf(
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

}