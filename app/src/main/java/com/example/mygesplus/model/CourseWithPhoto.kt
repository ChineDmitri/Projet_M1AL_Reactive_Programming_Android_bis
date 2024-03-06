package com.example.mygesplus.model

import androidx.room.Embedded
import androidx.room.Relation


class CourseWithPhotos {
    @Embedded
    var course: Course? = null

    @Relation(parentColumn = "id", entityColumn = "courseId")
    var photos: List<CoursePhoto>? = null
}