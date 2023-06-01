package com.example.yomakase.view.general.storedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.StoreDetailFacilityAdapter
import com.example.yomakase.adapter.StoreDetailPriceAdapter
import com.example.yomakase.databinding.FragmentStoreDetailInfoBinding
import com.example.yomakase.model.rv_item.Price
import com.example.yomakase.model.rv_item.StoreDetailFacility


class StoreDetailInfoFragment : Fragment() {
    private lateinit var binding: FragmentStoreDetailInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_detail_info, container, false)

        val list = listOf<Price>(
            Price("평일", "런치", 70000),
            Price("평일", "디너", 90000),
            Price("주말", "구분 없음", 120000)
        )
        binding.rvPrice.apply {
            this.adapter = StoreDetailPriceAdapter(list)
            this.layoutManager = LinearLayoutManager(activity)
        }

        val facilityList = listOf<StoreDetailFacility>(
            StoreDetailFacility(R.drawable.ic_facility_parking, "주차 가능"),
            StoreDetailFacility(R.drawable.ic_facility_no_kids, "노키즈 존"),
        )

        binding.rvFacilities.apply {
            this.adapter = StoreDetailFacilityAdapter(facilityList)
            this.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }

}