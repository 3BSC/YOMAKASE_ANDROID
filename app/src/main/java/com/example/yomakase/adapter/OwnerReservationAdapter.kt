package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemOwnerReservationBinding
import com.example.yomakase.model.rv_item.OwnerReservation

class OwnerReservationAdapter(private val itemList: List<OwnerReservation>): RecyclerView.Adapter<OwnerReservationAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvItemOwnerReservationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: OwnerReservation){
            binding.reservation = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemOwnerReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

}