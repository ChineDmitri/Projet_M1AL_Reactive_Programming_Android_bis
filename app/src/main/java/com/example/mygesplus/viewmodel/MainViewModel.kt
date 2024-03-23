package com.example.mygesplus.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mygesplus.App
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.CourseFb
import com.example.mygesplus.model.MainDb
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class MainViewModel(private val database: MainDb) : ViewModel() {
    //    private val db: MainDb = database
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val calendar: Calendar = Calendar.getInstance()
    private val _currentDate = MutableStateFlow(dateFormat.format(calendar.time))
    val currentDate = _currentDate.asStateFlow()

    private val _coursesList = MutableStateFlow<List<Course>>(emptyList())
    val coursesList: StateFlow<List<Course>> = _coursesList
    //    val coursesList : Flow<List<Course>> = database.dao.getCoursesByDate(currentDate.value)

    private fun getCoursesByCurrentDate() {
        viewModelScope.launch {
            database.dao.getCoursesByDate(currentDate.value).collect { fetchedCourses ->
                _coursesList.value = fetchedCourses
            }
        }
    }

    /* FIREBASE */
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val coursesRef = firebaseDatabase.getReference("courses")

    /*TEST FOR FIRE BASE*/
    private val courseWithoutId1 = CourseFb(
        id = coursesRef.key,
        nom = "Cours 3: Kotlin",
        date = Timestamp.valueOf("2022-01-03 00:00:00"),
        heureDebut = "9h00",
        heureFin = "11h00",
        description = "Super Kotlin course",
        isPresentiel = true
    )
    private val courseWithoutId2 = CourseFb(
        coursesRef.key,
        "Cours 4: RUST_MAN",
        Timestamp.valueOf("2022-02-03 00:00:00"),
        "9h00",
        "11h00",
        "Super Rustaman course",
        false
    )

    /*TEST FOR INSERT*/
    private val courseRoom = Course(
        UUID.randomUUID().toString(),
        "Cours 3: Kotlin",
        Timestamp.valueOf("2022-01-03 00:00:00"),
        "9h00",
        "11h00",
        "Super Kotlin course",
        true
    )


    init {
        this.getCoursesByCurrentDate()
        Log.wtf("KEY FOR COURSE1 before: ", courseWithoutId1.id)
        coursesRef.push().setValue(courseWithoutId1)
        coursesRef.push().setValue(courseWithoutId2)
        Log.wtf("KEY FOR COURSE1 after: ", courseWithoutId1.id)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                /*Pour test*/
            //                database.dao.insertCourse(courseRoom)
            }
        }

    }

    fun addDay() {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        _currentDate.value = dateFormat.format(calendar.time)
        getCoursesByCurrentDate()
    }

    fun subtractDay() {
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        _currentDate.value = dateFormat.format(calendar.time)
        getCoursesByCurrentDate()
    }


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
}