package com.moong.dailymission.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil (context: Context){
    val prefs : SharedPreferences = context.getSharedPreferences("prefs_name",Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String = ""): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return prefs.getInt(key, defValue)
    }

    fun setInt(key:String,num:Int){
        prefs.edit().putInt(key, num).apply()
    }
}