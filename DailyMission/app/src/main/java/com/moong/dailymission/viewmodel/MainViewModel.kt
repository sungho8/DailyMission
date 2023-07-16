package com.moong.dailymission.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moong.dailymission.model.Mission
import com.moong.dailymission.util.FireBaseManager

class MainViewModel : ViewModel() {
    val mutableData = MutableLiveData<MutableList<Mission>>()

    init {
        getMission()
    }

    private fun getMission(){
        FireBaseManager.readMission().observeForever{
            mutableData.value = it
        }
    }
}