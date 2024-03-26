package com.example.mygesplus.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mygesplus.App
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.MainDb
import com.example.mygesplus.model.dao.CourseDao
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCourseViewModel(private val database: MainDb) : ViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val db = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).dataBase
                return AddCourseViewModel(db) as T

            }
        }
    }

    fun isValidForm(nom: String, date: String, heureDebut: String, heureFin: String, description: String) : Boolean {
        return nom.isNotEmpty() && date.isNotEmpty() && heureDebut.isNotEmpty() && heureFin.isNotEmpty() && description.isNotEmpty()
    }

    fun submitToDatabase(
        nom: String,
        date: String,
        heureDebut: String,
        heureFin: String,
        description: String,
        isPresentiel: Boolean,
        applicationContext: Context,
        onSuccess: () -> Unit
    ){
        val db = Firebase.firestore

        val course = hashMapOf(
            "nom" to nom,
            "date" to date,
            "heureDebut" to heureDebut,
            "heureFin" to heureFin,
            "description" to description,
            "isPresentiel" to isPresentiel
        )

        db.collection("cours")
            .add(course)
            .addOnSuccessListener {documentReference ->
                viewModelScope.launch {
                    val courseRoom = Course(documentReference.id,nom,date,heureDebut,heureFin,description,isPresentiel)

                    insertCourseToRoom(courseRoom)
                }

                Toast.makeText(
                    applicationContext,
                    "Course ajouté avec succés",
                    Toast.LENGTH_SHORT
                ).show()

                onSuccess.invoke()
            }
    }

    private suspend fun insertCourseToRoom(course: Course) {
        withContext(Dispatchers.IO) {
            database.dao.insertCourse(course)
        }
    }

}