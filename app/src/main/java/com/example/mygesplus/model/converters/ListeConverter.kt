package com.example.mygesplus.model.converters

import androidx.room.TypeConverter
import com.example.mygesplus.model.Course
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListConverter {
    @TypeConverter
    fun fromJson(json: String?): List<Course> {
        val type: Type = object : TypeToken<List<Course>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<Course>): String {
        return Gson().toJson(list)
    }
}