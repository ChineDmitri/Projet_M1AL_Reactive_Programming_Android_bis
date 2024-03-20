package com.example.mygesplus.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.CoursePhoto
import com.example.mygesplus.model.CourseWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert
    fun insertCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Query("SELECT * FROM course")
    fun getAllCours(): Flow<List<Course>>

    @Query("SELECT * FROM course")
    fun getCoursesWithPhotos(): Flow<List<CourseWithPhotos>>

    @Transaction
    fun insertCourseWithPhotos(course: Course, photos: List<CoursePhoto>) {
        insertCourse(course) // Insert without photo
        photos.forEach { photo ->
            insertPhoto(photo) // Insert foreach photo of a course
        }
    }

    @Insert
    fun insertPhoto(photo: CoursePhoto)
}