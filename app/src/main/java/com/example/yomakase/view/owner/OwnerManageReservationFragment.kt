package com.example.yomakase.view.owner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.OwnerReservationAdapter
import com.example.yomakase.databinding.FragmentOwnerManageReservationBinding
import com.example.yomakase.model.rv_item.OwnerReservation

class OwnerManageReservationFragment : Fragment() {

    private lateinit var binding: FragmentOwnerManageReservationBinding
    private lateinit var activityContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_manage_reservation, container, false)
        activityContext = requireContext()

        val itemList = listOf<OwnerReservation>(
            OwnerReservation(1, "예약자 1", "010-1111-2222", "2023년 6월 8일 17:00", 3 ),
            OwnerReservation(2, "예약자 2", "010-1111-2222", "2023년 6월 8일 17:00", 3 ),
            OwnerReservation(3, "예약자 3", "010-1111-2222", "2023년 6월 8일 17:00", 3 ),
            OwnerReservation(4, "예약자 4", "010-1111-2222", "2023년 6월 8일 17:00", 3 )
        )

        binding.rvReservation.apply {
            this.adapter = OwnerReservationAdapter(itemList)
            this.layoutManager = LinearLayoutManager(activityContext)
        }

        return binding.root
    }
}