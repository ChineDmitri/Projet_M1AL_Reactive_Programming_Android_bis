package com.example.mygesplus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "course_photo",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE // In PROD -> RESTRICT
        )
    ]
)
data class CoursePhoto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "course_id") val courseId: Int,
    @ColumnInfo(name = "photoUrl") val photoUrl: String
)