package com.moong.dailymission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moong.dailymission.adapter.MissionAdapter
import com.moong.dailymission.databinding.ActivityMainBinding
import com.moong.dailymission.util.GlobalApplication
import com.moong.dailymission.viewmodel.MainViewModel
import org.json.JSONArray
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var model : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        model = ViewModelProvider(this).get(MainViewModel::class.java)

        // 매일매일 최초접속시 새로운 난수를 생성후 미션 하나 할당
        model.setTodayRandom()
        model.getTodayMission()

        setListener()
        setObserver()
    }

    private fun setListener(){
        binding.newTodayMission.setOnClickListener {
            model.addTodayMission()
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