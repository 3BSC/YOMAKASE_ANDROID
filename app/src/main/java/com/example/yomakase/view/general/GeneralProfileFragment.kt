package com.example.yomakase.view.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.yomakase.R
import com.example.yomakase.databinding.FragmentGeneralProfileBinding


class GeneralProfileFragment : Fragment() {
    private lateinit var binding: FragmentGeneralProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_profile, container, false)

        binding.btnEditProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_generalProfile_to_generalEditProfile)
        }

        return binding.root
    }

}