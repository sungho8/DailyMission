package com.moong.dailymission.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moong.dailymission.adapter.MissionAdapter
import com.moong.dailymission.databinding.ActivityMainBinding
import com.moong.dailymission.util.FireBaseManager
import com.moong.dailymission.util.GlobalApplication
import com.moong.dailymission.viewmodel.MainViewModel
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var model : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        model = ViewModelProvider(this).get(MainViewModel::class.java)

        setTodayRandom()
        setListener()
        setObserver()
    }

    fun setListener(){
        binding.newTodayMission.setOnClickListener {
            model.getRandomMission()
        }
    }

    private fun setTodayRandom(){
        val now = LocalDate.now().toString()
        val today = GlobalApplication.prefs.getString("today","")

        // 하루가 지났다면 새로운 난수를 생성
        if(now != today){
//            val size = GlobalApplication.prefs.getInt("size")
//            GlobalApplication.prefs.setString("today",now)
//            GlobalApplication.prefs.setInt("random",(0..size).random())
            model.getRandomMission()
        }
    }

    private fun setObserver(){
        model.mutableData.observe(this, Observer {
            val list = model.mutableData.value
            val adapter= MissionAdapter()
            adapter.data = list ?: mutableListOf()
            binding.missionList.adapter = adapter
        })
    }
}