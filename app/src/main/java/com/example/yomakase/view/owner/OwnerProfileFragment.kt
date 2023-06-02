package com.example.yomakase.view.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.yomakase.R
import com.example.yomakase.databinding.FragmentOwnerProfileBinding

class OwnerProfileFragment : Fragment() {
    private lateinit var binding: FragmentOwnerProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_profile, container, false)

        binding.btnEditProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_ownerProfile_to_ownerEditProfileFragment)
        }

        return binding.root
    }

}