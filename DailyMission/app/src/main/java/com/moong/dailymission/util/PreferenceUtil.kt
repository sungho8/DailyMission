package com.moong.dailymission.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class PreferenceUtil (context: Context){
    val prefs : SharedPreferences = context.getSharedPreferences("prefs_name",Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String = ""): String {
        Log.d("prefString","${prefs.getString(key,defValue)}")
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

    fun setIntArray(key:String,list:ArrayList<Int>){
        val json = GsonBuilder().create().toJson(
            list,
            object : TypeToken<ArrayList<Int>>() {}.type
        )
        GlobalApplication.prefs.setString(key,json.toString())
    }

    fun getIntArray(key:String, defValue:String = ""):ArrayList<Int>{
        val result = ArrayList<Int>()
        Log.d("presIntArray","${key} ${defValue} ${prefs.getString(key,defValue)}")

        val s = GlobalApplication.prefs.getString(key,defValue)
        val arrJson = JSONArray(s)

        for(i in 0 until arrJson.length()){
            result.add(arrJson.optInt(i))
        }

        return result
    }
}