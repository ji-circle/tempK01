package com.example.imageapi01.app

import android.app.Application

class ImageApiApplication: Application() {

    val appContainer = AppContainer()

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }

    companion object{
        private var INSTANCE: ImageApiApplication?= null
        fun getInstance() = INSTANCE
    }
}