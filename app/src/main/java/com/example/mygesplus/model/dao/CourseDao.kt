package com.example.mygesplus.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mygesplus.model.Course
import java.sql.Timestamp

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Query("SELECT * FROM cours WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): Course?

    @Query("SELECT * FROM cours WHERE date = :selectedDate")
    suspend fun getCoursesForDate(selectedDate: Timestamp): List<Course>

    @Query("SELECT * FROM cours WHERE isPresentiel = :isPresentiel")
    suspend fun getCoursesByPresentiel(isPresentiel: Boolean): List<Course>

    @Query("SELECT * FROM cours ORDER BY date DESC")
    suspend fun getAllCoursesSortedByDateDesc(): List<Course>

    @Query("SELECT * FROM cours WHERE nom LIKE '%' || :searchQuery || '%'")
    suspend fun searchCoursesByName(searchQuery: String): List<Course>

    @Query("DELETE FROM cours")
    suspend fun deleteAllCourses()
}