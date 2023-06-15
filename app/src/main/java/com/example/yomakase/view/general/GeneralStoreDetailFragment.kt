package com.example.yomakase.view.general

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.yomakase.R
import com.example.yomakase.adapter.ReservationViewPagerAdapter
import com.example.yomakase.databinding.FragmentGeneralStoreDetailBinding
import com.example.yomakase.dialog.ReservationDialog
import com.google.android.material.tabs.TabLayoutMediator

class GeneralStoreDetailFragment : Fragment() {
    private lateinit var binding: FragmentGeneralStoreDetailBinding
    private lateinit var activityContext: Context

    private val tabList = listOf<String>("소개", "정보", "오늘의 라인업")
    private var reservationDialog = ReservationDialog()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_store_detail, container, false)
        activityContext = requireActivity()

        binding.viewpager.adapter = ReservationViewPagerAdapter(activityContext as FragmentActivity)
        TabLayoutMediator(binding.tab, binding.viewpager){tab, pos ->
            tab.text = tabList[pos]
        }.attach()

        binding.btnReservate.setOnClickListener {
            reservationDialog.show((activityContext as FragmentActivity).supportFragmentManager, "reservation")
        }

        return inflater.inflate(R.layout.fragment_general_store_detail, container, false)
    }

}