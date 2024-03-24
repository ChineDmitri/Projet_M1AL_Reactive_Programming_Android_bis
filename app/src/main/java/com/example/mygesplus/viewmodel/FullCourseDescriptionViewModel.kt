package com.example.mygesplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mygesplus.App
import com.example.mygesplus.model.CoursePhoto
import com.example.mygesplus.model.MainDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FullCourseDescriptionViewModel(private val courseId: String, private val database: MainDb) : ViewModel() {


    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val db =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).dataBase
                return FullCourseDescriptionViewModel(courseId = "", db) as T
            }
        }
    }

    private val _photoCourseLiveData = MutableStateFlow<List<CoursePhoto>>(emptyList())
    val photoCourseLiveData: StateFlow<List<CoursePhoto>> = _photoCourseLiveData

    fun fetchPhotoForCourse(couseId: String): Unit {
        viewModelScope.launch {
            database.dao.getPhotosForCourse(couseId).collect { fetchedCourses ->
                _photoCourseLiveData.value = fetchedCourses
            }
        }
    }

    fun addPhotoToCourse(photo: CoursePhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            database.dao.insertPhoto(photo)
        }
    }

}