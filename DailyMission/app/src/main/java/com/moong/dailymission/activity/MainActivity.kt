package com.moong.dailymission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moong.dailymission.R

class MainActivity : AppCompatActivity() {
    val binding by lazy{  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}