package com.threegroup.tobedated

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    companion object {

    }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }
}