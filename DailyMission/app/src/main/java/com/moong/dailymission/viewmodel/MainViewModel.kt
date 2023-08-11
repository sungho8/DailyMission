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

     fun getRandomMission(){
        val randomList = ArrayList<Int>()
        val size = 4    // 현재 존재하는 총 미션

        // 기존에 없던 새로운 난수를 생성
        while(randomList.size != size){
            val r = (0..size-1).random()
            if(r !in randomList){
                randomList.add(r)
                break
            }
        }

        FireBaseManager.getTodayMission(randomList).observeForever {
            mutableData.value = it
        }
    }
}