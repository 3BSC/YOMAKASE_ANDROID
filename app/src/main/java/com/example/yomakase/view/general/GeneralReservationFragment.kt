package com.example.yomakase.view.general

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.GeneralFinishedReservationAdapter
import com.example.yomakase.adapter.GeneralUpcommingReservationAdapter
import com.example.yomakase.databinding.FragmentGeneralReservationBinding
import com.example.yomakase.model.rv_item.GeneralReservation


class GeneralReservationFragment : Fragment() {

    private lateinit var binding: FragmentGeneralReservationBinding
    private lateinit var activityContext : Context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activityContext = requireContext()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_reservation, container, false)

        setUpUpcommingReservationRv()
        setUpFinishedReservationRv()

        return binding.root
    }

    private fun setUpUpcommingReservationRv(){
        val allList = listOf<GeneralReservation>(
            GeneralReservation(1111, "병천순대1", "2022년 2월 28일 17:00", 1),
            GeneralReservation(2222, "병천순대2", "2022년 2월 28일 18:00", 2),
            GeneralReservation(3333, "병천순대3", "2022년 2월 28일 19:00", 3)
        )

        binding.rvUpCommingReservation.apply {
            this.adapter = GeneralUpcommingReservationAdapter(allList)
            this.layoutManager = LinearLayoutManager(activityContext)
        }
    }

    private fun setUpFinishedReservationRv(){
        val allList = listOf<GeneralReservation>(
            GeneralReservation(1111, "병천순대1", "2022년 2월 28일 17:00", 1),
            GeneralReservation(2222, "병천순대2", "2022년 2월 28일 18:00", 2),
            GeneralReservation(3333, "병천순대3", "2022년 2월 28일 19:00", 3)
        )

        binding.rvFinishedReservation.apply {
            this.adapter = GeneralFinishedReservationAdapter(allList)
            this.layoutManager = LinearLayoutManager(activityContext)
        }
    }
}