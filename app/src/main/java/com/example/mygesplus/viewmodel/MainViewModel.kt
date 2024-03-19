package com.example.mygesplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mygesplus.App
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.MainDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp

class MainViewModel(database: MainDb) : ViewModel() {

    val courseWithoutId = Course(
        nom = "Cours 3: Kotlin",
        date = Timestamp.valueOf("2022-01-03 00:00:00"),
        heureDebut = "9h00",
        heureFin = "11h00",
        description = "Super Kotlin course",
        isPresentiel = true
    )

    private val course1 = Course(
        null,
        "Cours 3: Kotlin",
        Timestamp.valueOf("2022-01-03 00:00:00"),
        "9h00",
        "11h00",
        "Super Kotlin course",
        true
    )
    private val course2 = Course(
        null,
        "Cours 4: RUST_MAN",
        Timestamp.valueOf("2022-01-03 00:00:00"),
        "9h00",
        "11h00",
        "Super Rustaman course",
        false
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.dao.insertCourse(course1)
                database.dao.insertCourse(course2)
            }
        }
    }

    val coursesList = database.dao.getAllCours()


    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val db = (checkNotNull(extras[APPLICATION_KEY]) as App).dataBase
                return MainViewModel(db) as T
            }
        }
    }

    /*private val _courses: MutableState<List<Course>> = mutableStateOf(
        listOf(
            Course(
                1,
                "Cours 1",
                Timestamp.valueOf("2022-01-01 00:00:00"),
                "8h00",
                "10h00",
                "Super Android course",
                true,
            ),
            Course(
                2,
                "Cours 2 : Rust",
                Timestamp.valueOf("2022-01-02 00:00:00"),
                "12h00",
                "13h00",
                "Super Rustaman course",
                true,
            )
        )
    )*/


//    val courses: MutableState<List<Course>> get() = _courses
}