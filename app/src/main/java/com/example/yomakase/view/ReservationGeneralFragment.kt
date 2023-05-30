package com.example.yomakase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.yomakase.R
import com.example.yomakase.databinding.FragmentReservationGeneralBinding


class ReservationGeneralFragment : Fragment() {

    private lateinit var binding: FragmentReservationGeneralBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_general, container, false)

        return binding.root
    }
}