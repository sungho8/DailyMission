package com.moong.dailymission.util

import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moong.dailymission.model.Mission
import com.moong.dailymission.model.User

object FireBaseManager {
    private val store by lazy{ FirebaseFirestore.getInstance() }

    fun setUser(user : User){
        val uid = GlobalApplication.prefs.getString("uid")
        user.uid = uid
        store.collection("User").document(uid).set(user)
            .addOnSuccessListener { Log.d("FireBaseManager","성공") }
            .addOnFailureListener { Log.d("FireBaseManager","실패") }
    }


    // 새로운 미션 하나를 받아온다.
    fun getTodayMission(randomList : ArrayList<Int>): LiveData<MutableList<Mission>> {
        val mutableData = MutableLiveData<MutableList<Mission>>()

        store.collection("Mission").get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val listData: MutableList<Mission> = mutableListOf<Mission>()
                    for((index,mission) in (task.result ?: listOf()).withIndex()){
                        // 난수에 해당하는 미션이면 뽑아낸다.
                        if(index in randomList){
                            val m = Mission(title = mission.data["title"].toString())
                            listData.add(m)
                            Log.d("NewMission","${randomList} ${m}")
                        }
                    }
                    mutableData.value = listData
                }else{
                    Log.d("Failure","데이터 받아오기 실패 ${task.result}")
                }
            }

        return mutableData
    }
}