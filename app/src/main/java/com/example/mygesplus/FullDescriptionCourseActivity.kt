package com.example.mygesplus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mygesplus.model.Course
import com.example.mygesplus.view.FullCourseDescriptionView
import com.google.gson.Gson


class FullDescriptionCourseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val str = intent.getStringExtra("cours")
        Log.wtf("courseSelected str", str)

        val course = Gson().fromJson(str, Course::class.java)
        Log.wtf("courseSelected class", course.description)

        setContent {
            FullCourseDescriptionView(course)
        }
    }
}

