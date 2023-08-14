package com.moong.dailymission.model

data class User(
    var uid : String,
    var dailyMission : ArrayList<Mission>,
    val todayMission : ArrayList<Mission>
)