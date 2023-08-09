package com.moong.dailymission.util

import android.app.Application
import android.content.Context

class GlobalApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var prefs: PreferenceUtil

        var instance:GlobalApplication? = null
        fun context() : Context? {
            return instance?.applicationContext
        }
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}