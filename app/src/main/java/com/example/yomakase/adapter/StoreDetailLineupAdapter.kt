package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemStoreDetailLineupBinding

class StoreDetailLineupAdapter(private val itemList: List<String>): RecyclerView.Adapter<StoreDetailLineupAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemStoreDetailLineupBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.menu = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemStoreDetailLineupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}