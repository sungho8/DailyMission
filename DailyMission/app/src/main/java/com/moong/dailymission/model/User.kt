package com.moong.dailymission.model

data class User(
    val dailyMission : ArrayList<Mission>,
    val todayMission : ArrayList<Mission>
)