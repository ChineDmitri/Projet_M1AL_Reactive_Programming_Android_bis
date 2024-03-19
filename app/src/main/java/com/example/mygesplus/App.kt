package com.example.mygesplus

import android.app.Application
import android.util.Log
import com.example.mygesplus.model.MainDb

class App : Application() {
    val dataBase by lazy {
        Log.wtf("BD::ROOM", "Init BD")
        MainDb.createDataBase(this)
    }
}