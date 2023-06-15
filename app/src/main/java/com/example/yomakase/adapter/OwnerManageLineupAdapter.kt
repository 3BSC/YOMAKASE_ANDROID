package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemOwnerLineupBinding
import com.example.yomakase.util.ItemTouchHelperListener

class OwnerManageLineupAdapter(val itemList: ArrayList<String>): RecyclerView.Adapter<OwnerManageLineupAdapter.ViewHolder>(), ItemTouchHelperListener {

    inner class ViewHolder(val binding: RvItemOwnerLineupBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnDelete.setOnClickListener {
                val pos = adapterPosition
                itemList.removeAt(pos)
                notifyDataSetChanged()
            }
        }
        fun bind(item: String){
            binding.tvMenu.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemOwnerLineupBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun onItemMove(from: Int, to: Int) {
        val item: String = itemList[from]
        itemList.removeAt(from)
        itemList.add(to, item)
        notifyItemMoved(from, to)
    }



}