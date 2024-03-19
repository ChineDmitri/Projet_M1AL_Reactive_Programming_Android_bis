package com.example.mygesplus.model.converters

import android.util.Log
import androidx.room.TypeConverter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

class DateConverters {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    @TypeConverter
    fun fromString(value: String?): Timestamp? {
        return value?.let {
            try {
                val date = dateFormat.parse(it)
                return Timestamp(date.time)
            } catch (e: Exception) {
                /*e.printStackTrace()*/
                Log.wtf(
                    DateConverters::class::simpleName.toString(),
                    "fromString: "
                )
                null
            }
        }
    }

    @TypeConverter
    fun toString(timestamp: Timestamp?): String? {
        return timestamp?.let {
            dateFormat.format(Date(it.time))
        }
    }
}