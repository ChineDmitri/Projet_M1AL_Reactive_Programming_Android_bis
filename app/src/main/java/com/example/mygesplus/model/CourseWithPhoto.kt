package com.example.mygesplus.model

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithPhotos(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val coursePhotos: List<CoursePhoto>
)