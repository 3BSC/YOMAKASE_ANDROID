package com.example.yomakase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yomakase.databinding.RvItemFilteredStoreBinding
import com.example.yomakase.databinding.RvItemMainStoreBinding
import com.example.yomakase.model.rv_item.FilteredStoreOverView
import com.example.yomakase.model.rv_item.MainStoreOverView

class FilteredStoreOverviewAdapter(private val context: Context, private val filteredStoreOverviewList: List<FilteredStoreOverView>)
    : RecyclerView.Adapter<FilteredStoreOverviewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: RvItemFilteredStoreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: FilteredStoreOverView){
            binding.store = item

            Glide.with(context)
                .load(item.imageUrl)
                .centerInside()
                .into(binding.ivStore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemFilteredStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = filteredStoreOverviewList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredStoreOverviewList[position])
    }

}