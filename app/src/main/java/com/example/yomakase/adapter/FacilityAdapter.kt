package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.model.rv_item.DialogFacility
import com.example.yomakase.databinding.RvItemFacilityBinding

class FacilityAdapter(private val itemList: List<DialogFacility>): RecyclerView.Adapter<FacilityAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemFacilityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: DialogFacility){
            binding.facility = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RvItemFacilityBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}