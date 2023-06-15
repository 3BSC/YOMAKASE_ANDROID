package com.example.yomakase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yomakase.databinding.RvItemOwnerStoreOverviewBinding
import com.example.yomakase.model.rv_item.OwnerStoreOverview

class OwnerAllStoreAdapter(private val itemList: List<OwnerStoreOverview>, private val context: Context)
    : RecyclerView.Adapter<OwnerAllStoreAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemOwnerStoreOverviewBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: OwnerStoreOverview){
            binding.store = item
            Glide.with(context)
                .load(item.imgUrl)
                .centerInside()
                .into(binding.ivStore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemOwnerStoreOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}