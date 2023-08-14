package com.moong.dailymission.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moong.dailymission.model.Mission
import com.moong.dailymission.util.FireBaseManager
import com.moong.dailymission.util.GlobalApplication
import java.time.LocalDate

class MainViewModel : ViewModel() {
    val mutableData = MutableLiveData<MutableList<Mission>>()

    fun setTodayRandom(){
        val now = LocalDate.now().toString()
        val today = GlobalApplication.prefs.getString("today","")

        // 하루가 지났다면 새로운 난수를 생성
        if(now != today){
            // 새로운 랜덤리스트
            GlobalApplication.prefs.setIntArray("random",arrayListOf())
            GlobalApplication.prefs.setString("today",now)
            addTodayMission()
        }
    }

     fun addTodayMission(){
        val randomList = GlobalApplication.prefs.getIntArray("random")
        val size = 10    // 현재 존재하는 총 미션

        // 기존에 없던 새로운 난수를 생성
        while(randomList.size != size){
            val r = (0 until size).random()
            if(r !in randomList){
                randomList.add(r)
                break
            }
        }
        getTodayMission()
    }


    // 현재 할당된 난수로 오늘의 미션을 불러온다.
    fun getTodayMission(){
        val randomList = GlobalApplication.prefs.getIntArray("random")
        FireBaseManager.getTodayMission(randomList).observeForever {
            mutableData.value = it
        }
    }

}