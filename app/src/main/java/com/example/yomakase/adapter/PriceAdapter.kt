package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.model.rv_item.Price
import com.example.yomakase.databinding.RvItemPriceBinding

class PriceAdapter(private val priceList:List<Price>, val onClickRemoveBtn: (price: Price) -> Unit): RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemPriceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Price){
            binding.price = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = RvItemPriceBinding.inflate(inflater, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return priceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(priceList[position])
        holder.binding.btnRemove.setOnClickListener {
            onClickRemoveBtn.invoke(priceList[position])
        }
    }
}