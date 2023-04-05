package com.example.yomakase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yomakase.databinding.RvItemDlgFacilityBinding

class DlgFacilityAdapter(private val itemList: List<DialogFacility>
    ,val onCheckedListener: (isChecked: Boolean, facility: DialogFacility) -> Unit): RecyclerView.Adapter<DlgFacilityAdapter.ViewHolder>()
{
        inner class ViewHolder(val binding: RvItemDlgFacilityBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: DialogFacility){
                binding.ivFacility.setImageResource(item.img)
                binding.facility = item
                if(item.checked){
                    binding.cbPossible.isChecked = true
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemFacilityBinding = RvItemDlgFacilityBinding.inflate(inflater, parent, false)
        return ViewHolder(itemFacilityBinding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.binding.cbPossible.setOnCheckedChangeListener { compoundButton, b ->
            onCheckedListener(b, holder.binding.facility!!)
        }
    }
}