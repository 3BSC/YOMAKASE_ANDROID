package com.example.yomakase.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yomakase.view.storedetail.*

class ReservationViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> StoreDetailExplainFragment()
            1 -> StoreDetailInfoFragment()
            else -> StoreDetailLineupFragment()
        }
    }
}