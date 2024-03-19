package com.example.mygesplus.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mygesplus.model.converters.DateConverters
import com.example.mygesplus.model.converters.ListConverter
import com.example.mygesplus.model.dao.CourseDao


@Database(
    entities = [
        Course::class,
        CoursePhoto::class
    ],
    version = 1,
    /*exportSchema = false*/
)
@TypeConverters(value = [DateConverters::class, ListConverter::class])
abstract class MainDb : RoomDatabase() {

    abstract val dao: CourseDao

    companion object {
        fun createDataBase(context: Context): MainDb {
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "mygesplus.db"
            ).build()
        }
    }

}