package com.example.yomakase.view.storedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.PriceAdapter
import com.example.yomakase.adapter.StoreDetailPriceAdapter
import com.example.yomakase.databinding.FragmentStoreDetailExplainBinding
import com.example.yomakase.databinding.FragmentStoreDetailInfoBinding
import com.example.yomakase.model.rv_item.Price


class StoreDetailExplainFragment : Fragment() {
    private lateinit var binding: FragmentStoreDetailExplainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_detail_explain, container, false)

        return binding.root
    }

}