package com.moong.dailymission.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.moong.dailymission.model.Mission

object FireBaseManager {
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val ref = database.getReference("mission")

    fun userRef(path : String) = database.getReference(path)

    fun write(path : String, key : String, value: String){
        userRef(path)
        ref.child(key).setValue(value)
    }

    fun readMission(): LiveData<MutableList<Mission>> {
        val mutableData = MutableLiveData<MutableList<Mission>>()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            val listData: MutableList<Mission> = mutableListOf<Mission>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(Mission::class.java)
                        Log.d("SnapMission","${getData} ")
                        getData?.let { listData.add(it) }
                    }
                    mutableData.value = listData
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableData
    }
}