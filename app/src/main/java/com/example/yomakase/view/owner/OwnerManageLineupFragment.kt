package com.example.yomakase.view.owner

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.OwnerManageLineupAdapter
import com.example.yomakase.databinding.FragmentOwnerManageLineupBinding
import com.example.yomakase.util.ItemTouchCallback

class OwnerManageLineupFragment : Fragment() {

    private lateinit var binding: FragmentOwnerManageLineupBinding
    private lateinit var activityContext: Context

    private val itemList by lazy { arrayListOf("에피타이저 1",  "디저트 2","에피타이저 1") }
    private val recyclerViewAdapter by lazy {
        Log.d("TAG", "adapter")
        OwnerManageLineupAdapter(itemList)
    }
    private val itemTouchHelper by lazy { ItemTouchHelper(ItemTouchCallback(recyclerViewAdapter)) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_manage_lineup, container, false)
        activityContext = requireContext()

        binding.rvLineUp.apply {
            this.adapter = recyclerViewAdapter
            this.layoutManager = LinearLayoutManager(activityContext)
        }
        itemTouchHelper.attachToRecyclerView(binding.rvLineUp)

        binding.btnAddMenu.setOnClickListener {
            itemList.add(binding.etMenu.text.toString())
            recyclerViewAdapter.notifyDataSetChanged()
        }

        binding.btnOk.setOnClickListener {
            Log.d("TAG", recyclerViewAdapter.itemList.toString())
        }


        return binding.root
    }
}