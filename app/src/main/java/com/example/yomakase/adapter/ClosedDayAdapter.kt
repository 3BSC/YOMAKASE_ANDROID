package com.example.yomakase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.model.ClosedDay
import com.example.yomakase.databinding.RvItemClosedDayBinding

class ClosedDayAdapter(private val closedDayList:List<ClosedDay>, val onClickItemListener: (closedDay: View, position: Int) -> Unit)
    : RecyclerView.Adapter<ClosedDayAdapter.ViewHolder>()
{

    inner class ViewHolder(val binding: RvItemClosedDayBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ClosedDay){
            binding.closeday = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = RvItemClosedDayBinding.inflate(inflater, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount() = closedDayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(closedDayList[position])
        holder.binding.btnDay.setOnClickListener {
             onClickItemListener(it, position)
        }
    }
}