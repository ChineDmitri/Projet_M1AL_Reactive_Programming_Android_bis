package com.example.mygesplus

import android.app.Application
import com.example.mygesplus.model.MainDb

class App: Application() {
    val dataBase by lazy {
        MainDb.createDataBase(this)
    }
}