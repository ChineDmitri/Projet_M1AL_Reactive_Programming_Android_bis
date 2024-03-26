package com.example.mygesplus.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.CoursePhoto
import com.example.mygesplus.model.CourseWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Course)

    @Query("SELECT * FROM course WHERE id = :courseId")
    fun getCourseById(courseId: String): Course?

    @Update
    fun updateCourse(course: Course)

    @Transaction
    fun insertOrUpdateCourse(course: Course) {
        val existingCourse = getCourseById(course.id)
        if (existingCourse == null) {
            insertCourse(course)
        } else {
            updateCourse(course)
        }
    }

    @Delete
    fun deleteCourse(course: Course)

    @Query("SELECT * FROM course")
    fun getAllCours(): Flow<List<Course>>

    @Query("SELECT * FROM course WHERE date = :dateString ORDER BY heure_debut ASC")
    fun getCoursesByDate(dateString: String): Flow<List<Course>>

    @Query("SELECT * FROM course")
    fun getCoursesWithPhotos(): Flow<List<CourseWithPhotos>>

    @Transaction
    fun insertCourseWithPhotos(course: Course, photos: List<CoursePhoto>) {
        insertCourse(course) // Insert without photo
        photos.forEach { photo ->
            insertPhoto(photo) // Insert foreach photo of a course
        }
    }

    @Transaction
    fun addPhotosToCourse(courseId: String, photos: List<CoursePhoto>) {
        val photosWithCourseId = photos.map { photo ->
            photo.copy(courseId = courseId)
        }
        insertPhotos(photosWithCourseId)
    }


    @Query("SELECT * FROM course_photo WHERE course_id = :courseId")
    fun getPhotosForCourse(courseId: String): Flow<List<CoursePhoto>>

    @Insert
    fun insertPhoto(photo: CoursePhoto)

    @Query("DELETE FROM course_photo WHERE id = :photoId")
    fun deletePhoto(photoId: Int)

    @Insert
    fun insertPhotos(photos: List<CoursePhoto>)



}