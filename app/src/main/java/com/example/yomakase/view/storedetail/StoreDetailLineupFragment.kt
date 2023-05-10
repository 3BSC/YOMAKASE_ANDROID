package com.example.yomakase.view.storedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.StoreDetailLineupAdapter
import com.example.yomakase.databinding.FragmentStoreDetailLineupBinding

class StoreDetailLineupFragment : Fragment() {
    private lateinit var binding: FragmentStoreDetailLineupBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_detail_lineup, container, false)

        val list = listOf<String>("appetizer 1", "appetizer 2", "appetizer 3",
            "main 1", "main 2", "main 3", "main 4", "main 5", "main 6"
            , "dessert 1", "dessert 2" )
        binding.rvLineup.apply {
            this.adapter = StoreDetailLineupAdapter(list)
            this.layoutManager = LinearLayoutManager(activity)
        }

        return binding.root
    }

}