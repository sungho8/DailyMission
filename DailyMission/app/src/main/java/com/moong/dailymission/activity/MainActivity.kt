package com.moong.dailymission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.moong.dailymission.R
import com.moong.dailymission.adapter.MissionAdapter
import com.moong.dailymission.databinding.ActivityMainBinding
import com.moong.dailymission.model.Mission
import com.moong.dailymission.util.FireBaseManager
import com.moong.dailymission.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var model : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        model = ViewModelProvider(this).get(MainViewModel::class.java)

        setObserver()
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