package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemOwnerCourseTimeBinding
import com.example.yomakase.databinding.RvItemOwnerLineupBinding

class OwnerEditStoreInfoCourseTimeAdapter(private val itemList: ArrayList<String>): RecyclerView.Adapter<OwnerEditStoreInfoCourseTimeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemOwnerCourseTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRemove.setOnClickListener {
                val pos = adapterPosition
                itemList.removeAt(pos)
                notifyDataSetChanged()
            }
        }

        fun bind(item: String) {
            binding.time = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemOwnerCourseTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }


}