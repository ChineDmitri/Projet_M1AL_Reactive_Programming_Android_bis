package com.example.mygesplus.model.converters

import androidx.room.TypeConverter
import java.sql.Timestamp

class DateConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun toTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time
    }
}