package com.example.mygesplus

import android.app.Application
import android.util.Log
import com.example.mygesplus.model.MainDb
import com.example.mygesplus.viewmodel.AddCourseViewModel
import com.google.firebase.FirebaseApp

class App : Application() {
    val dataBase by lazy {
        Log.wtf("BD::ROOM", "Init BD")
        MainDb.createDataBase(this)
    }
}