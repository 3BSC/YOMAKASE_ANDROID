package com.example.yomakase.view.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.yomakase.R
import com.example.yomakase.databinding.FragmentOwnerManageStoreBinding
import com.example.yomakase.model.rv_item.OwnerStoreOverview

class OwnerManageStoreFragment : Fragment() {
    private lateinit var binding: FragmentOwnerManageStoreBinding
    val testUrl = "https://static.wixstatic.com/media/ebc3dd_0797491cd9b24205acbbc2c15a5be395~mv2_d_2668_4000_s_4_2.jpg/v1/crop/x_0,y_87,w_2668,h_3826/fill/w_560,h_802,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/_DSC6777-Edit.jpg"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_manage_store, container, false)

        Glide.with(binding.root).load(testUrl).centerInside().into(binding.storeOverview.ivStore)
        binding.storeOverview.store = OwnerStoreOverview(testUrl, "가게이름 2")

        binding.btnEditLineup.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_ownerManageStoreFragment_to_ownerManageLineupFragment)
        }
        return binding.root
    }
}