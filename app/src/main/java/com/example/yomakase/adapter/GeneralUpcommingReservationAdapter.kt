package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemGeneralUpcommingReservationBinding
import com.example.yomakase.model.rv_item.GeneralReservation

class GeneralUpcommingReservationAdapter(private val itemList: List<GeneralReservation>)
    : RecyclerView.Adapter<GeneralUpcommingReservationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemGeneralUpcommingReservationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: GeneralReservation){
            binding.reservation = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemGeneralUpcommingReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}