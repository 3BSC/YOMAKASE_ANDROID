package com.example.yomakase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.yomakase.R
import com.example.yomakase.databinding.FragmentProfileGeneralBinding


class ProfileGeneralFragment : Fragment() {
    private lateinit var binding: FragmentProfileGeneralBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_general, container, false)
        return binding.root
    }

}