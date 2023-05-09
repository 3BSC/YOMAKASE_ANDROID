package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemStoreDetailInfoPriceBinding
import com.example.yomakase.model.rv_item.Price

class StoreDetailPriceAdapter(private val itemList: List<Price>): RecyclerView.Adapter<StoreDetailPriceAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemStoreDetailInfoPriceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Price){
            binding.price = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemStoreDetailInfoPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}