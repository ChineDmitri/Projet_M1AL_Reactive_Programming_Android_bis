package com.example.mygesplus.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class MainViewModel(private val database: MainDb) : ViewModel() {
    //    private val db: MainDb = database
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val calendar: Calendar = Calendar.getInstance()
    private val _currentDate = MutableStateFlow(dateFormat.format(calendar.time))
    val currentDate = _currentDate.asStateFlow()

    private val _coursesList = MutableStateFlow<List<Course>>(emptyList())
    val coursesList: StateFlow<List<Course>> = _coursesList
    private fun getCoursesByCurrentDate() {
        viewModelScope.launch {
            database.dao.getCoursesByDate(currentDate.value).collect { fetchedCourses ->
                _coursesList.value = fetchedCourses
            }
        }
    }

    init {
        this.getCoursesByCurrentDate()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // init Firestore
                val db = Firebase.firestore
                // Récupération des données de Firestore
                val querySnapshot = db.collection("cours").get().await()
                val courses = mutableListOf<CourseFb>()
                for (document in querySnapshot.documents) {
                    val course = CourseFb(
                        id = document.id,
                        nom = document.getString("nom") ?: "",
                        date = document.getString("date") ?: "",
                        heureDebut = document.getString("heureDebut") ?: "",
                        heureFin = document.getString("heureFin") ?: "",
                        description = document.getString("description") ?: "",
                        isPresentiel = document.getBoolean("isPresentiel") ?: false
                    )
                    courses.add(course)
                }
                // Insertion dans Room
                for (course in courses) {
                    val roomCourse = Course(
                        id = course.id,
                        nom = course.nom,
                        date = course.date,
                        heureDebut = course.heureDebut,
                        heureFin = course.heureFin,
                        description = course.description,
                        isPresentiel = course.isPresentiel
                    )

                    database.dao.updateCourse(roomCourse)
                }
            }
        }
    }

    fun isCoursInBdd(id: String) {

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