package com.example.yomakase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.bumptech.glide.Glide
import com.example.yomakase.R
import com.example.yomakase.databinding.RvItemMainStoreBinding
import com.example.yomakase.model.rv_item.MainStoreOverView

class MainStoreOverviewAdapter(private val context: Context, private val storeOverviewList: List<MainStoreOverView>)
    : RecyclerView.Adapter<MainStoreOverviewAdapter.ViewHolder>(){

        inner class ViewHolder(val binding: RvItemMainStoreBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: MainStoreOverView){
                binding.store = item

                Glide.with(context)
                    .load(item.imageUrl)
                    .centerInside()
                    .into(binding.ivStore)


                if(adapterPosition==0 || adapterPosition == storeOverviewList.size-1){
                    binding.rvLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

                    val displayMetrics = context.resources.displayMetrics
                    val screenWidth = displayMetrics.widthPixels
                    var mLayoutParam : RecyclerView.LayoutParams =  binding.rvLayout.layoutParams as RecyclerView.LayoutParams
                    if(adapterPosition == 0)
                        mLayoutParam.leftMargin = (screenWidth - binding.rvLayout.measuredWidthAndState)/2
                    else
                        mLayoutParam.rightMargin = (screenWidth - binding.rvLayout.measuredWidthAndState)/2
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemMainStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = storeOverviewList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeOverviewList[position])
    }

}