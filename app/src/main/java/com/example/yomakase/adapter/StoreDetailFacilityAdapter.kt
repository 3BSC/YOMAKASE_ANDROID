package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemStoreDetailInfoFacilityBinding
import com.example.yomakase.model.rv_item.StoreDetailFacility

class StoreDetailFacilityAdapter(private val itemList: List<StoreDetailFacility>): RecyclerView.Adapter<StoreDetailFacilityAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemStoreDetailInfoFacilityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: StoreDetailFacility){
            binding.ivFacility.setImageResource(item.img)
            binding.facility = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemStoreDetailInfoFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}