package com.example.mygesplus.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mygesplus.model.Course
import com.example.mygesplus.model.CourseWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert
    fun insertCourse(course: Course) : Unit

    @Delete
    fun deleteCourse(course: Course) : Unit

//    @Query("SELECT * FROM course")
//    fun getAllCours(): Flow<List<Course>>

    @Query("SELECT * FROM course")
    fun getCoursesWithPhotos(): Flow<List<CourseWithPhotos>>
}


/*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Update
    suspend fun updateCourse(course: Course)



    @Query("SELECT * FROM cours WHERE id = :courseId LIMIT 1")
    fun getCourseById(courseId: Int): LiveData<Course>
    //    use ->
    *//*liveDataCourse.observe(this, { course ->
    if (course != null) {
        // Traitement ok
    } else {
        // Error
    }
    })*//*

    @Query("SELECT * FROM cours WHERE date = :selectedDate")
    fun getCoursesForDate(selectedDate: Timestamp): LiveData<List<Course>>

    @Query("SELECT * FROM cours WHERE isPresentiel = :isPresentiel")
    fun getCoursesByPresentiel(isPresentiel: Boolean): LiveData<List<Course>>

    @Query("SELECT * FROM cours ORDER BY date DESC")
    fun getAllCoursesSortedByDateDesc(): LiveData<List<Course>>

    @Query("SELECT * FROM cours WHERE nom LIKE '%' || :searchQuery || '%'")
    fun searchCoursesByName(searchQuery: String): LiveData<List<Course>>

    @Query("DELETE FROM cours")
    fun deleteAllCourses(): Int*/