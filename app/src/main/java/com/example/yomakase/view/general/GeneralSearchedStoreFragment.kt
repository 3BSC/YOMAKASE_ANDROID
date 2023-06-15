package com.example.yomakase.view.general

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.R
import com.example.yomakase.adapter.FilteredStoreOverviewAdapter
import com.example.yomakase.databinding.FragmentGeneralSearchedStoreBinding
import com.example.yomakase.model.rv_item.FilteredStoreOverView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class GeneralSearchedStoreFragment : Fragment() {
    private lateinit var binding: FragmentGeneralSearchedStoreBinding
    private lateinit var activityContext : Context

    val allUrl = "data:image/jpeg;base64,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"
    private val cal: Calendar = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var filterCategories = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_searched_store, container, false)
        activityContext  =requireContext()
        setUpRv()

        binding.btnFilter.setOnClickListener {
            showBottomFilterDialog()
        }

        return binding.root
    }

    private fun setUpRv(){
        val list = listOf<FilteredStoreOverView>(
            FilteredStoreOverView(allUrl, "name1", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name2", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name3", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name4", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name5", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name6", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name7", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name8", 4.8f, "address1", "tel1", "오마카세"),
            FilteredStoreOverView(allUrl, "name9", 4.8f, "address1", "tel1", "오마카세"),
        )
        binding.rvAll.apply {
            this.adapter = FilteredStoreOverviewAdapter(activityContext, list)
            this.layoutManager = LinearLayoutManager(activityContext)
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showBottomFilterDialog(){
        val view = layoutInflater.inflate(R.layout.dlg_bottom_filter, null)
        val dlg = BottomSheetDialog(activityContext)
        dlg.setContentView(view)

        val date = view.findViewById<TextView>(R.id.tvFilterDay)
        val people = view.findViewById<EditText>(R.id.etFilterPeopole)
        val region = view.findViewById<EditText>(R.id.etFilterRegion)
        val btnAll = view.findViewById<AppCompatButton>(R.id.btnFilterAll)
        val btnOmakase = view.findViewById<AppCompatButton>(R.id.btnFilterOmakase)
        val btnUmakase = view.findViewById<AppCompatButton>(R.id.btnFilterUmakase)
        val btnDonmakase = view.findViewById<AppCompatButton>(R.id.btnFilterDonmakase)
        val btnOthers = view.findViewById<AppCompatButton>(R.id.btnFilterOthers)
        val btnArray = arrayOf(btnOmakase, btnUmakase, btnDonmakase, btnOthers)
        val categoryList = listOf<AppCompatButton>(btnOmakase, btnUmakase, btnDonmakase, btnOthers)

        date.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                date.text = dateFormat.format(Date(year- 1900, month, dayOfMonth))
            }
            DatePickerDialog(activityContext, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        btnAll.setOnClickListener {
            it as AppCompatButton
            //꺼져있는 버튼만 모두 켜기
            if (it.tag == "1"){
                it.tag = "0"
                it.setBackgroundResource(R.drawable.btn_login)
                categoryList.forEach {btn ->
                    if (!filterCategories.contains(btn.tag))
                        btn.callOnClick()
                }
            }//켜져있는 버튼 끄기
            else{
                it.tag = "1"
                it.setBackgroundResource(R.drawable.btn_unclicked)
                categoryList.forEach { btn ->
                    if (filterCategories.contains(btn.tag))
                        btn.callOnClick()
                }
            }
        }

        btnArray.forEach {
            it.setOnClickListener { view ->
                it as AppCompatButton
                if (!filterCategories.contains(it.tag.toString())) {
                    it.setBackgroundResource(R.drawable.btn_login)
                    filterCategories.add(it.tag.toString())
                }else{
                    it.setBackgroundResource(R.drawable.btn_unclicked)
                    filterCategories.remove(it.tag.toString())
                }
            }
        }

        dlg.show()
    }
}