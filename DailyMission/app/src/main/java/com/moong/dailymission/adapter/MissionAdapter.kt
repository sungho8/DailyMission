package com.moong.dailymission.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moong.dailymission.databinding.ItemMissionBinding
import com.moong.dailymission.model.Mission

class MissionAdapter : RecyclerView.Adapter<MissionAdapter.MenuViewHolder>(){
    var data = mutableListOf<Mission>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMissionBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.apply {
            holder.onBind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long = position.toLong()

    inner class MenuViewHolder(val binding : ItemMissionBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : Mission) {
            binding.mission = data
        }
    }
}