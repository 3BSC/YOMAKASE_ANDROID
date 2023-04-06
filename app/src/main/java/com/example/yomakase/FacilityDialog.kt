package com.example.yomakase

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.databinding.DlgFacilitiesBinding

class FacilityDialog(facilityDialogInterface: FacilityDialogInterface, context: Context,  var preSelected: List<DialogFacility>): DialogFragment(){
    private var _binding: DlgFacilitiesBinding? = null
    private val binding get() = _binding!!
    private var facilityDialogInterface: FacilityDialogInterface? = null
    private val selected = mutableListOf<DialogFacility>()
    private var dlgFacilityList = mutableListOf<DialogFacility>(
        DialogFacility(R.drawable.ic_facility_parking, "주차 가능", 1, false),
        DialogFacility(R.drawable.ic_facility_valet, "발렛 파킹", 2, false),
        DialogFacility(R.drawable.ic_facility_corkage_free, "콜키지 프리", 3, false),
        DialogFacility(R.drawable.ic_facility_corkage_possible, "콜키지 가능", 4, false),
        DialogFacility(R.drawable.ic_facility_no_kids, "노키즈 존", 5, false),
        DialogFacility(R.drawable.ic_facility_dog, "반려동물 동반", 6, false)
    )

    init {
        this.facilityDialogInterface = facilityDialogInterface
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DlgFacilitiesBinding.inflate(inflater, container ,false)

        dlgFacilityList.forEach {
            if(preSelected.contains(it)){
                it.checked = true
            }
        }

        binding.rvDlgFacilities.apply {
            adapter = DlgFacilityAdapter(dlgFacilityList, onCheckedListener = {isChecked, facility ->
                if(isChecked)
                    selected.add(facility)
                else
                    selected.remove(facility)
            })
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnDlgOk.setOnClickListener {
            selected.sortBy { it.number }
            this.facilityDialogInterface?.onOkBtnClicked(selected)
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        resizeDialog()
    }

    private fun resizeDialog() {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.8).toInt()
        params?.height = (deviceHeight * 0.55).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}

interface FacilityDialogInterface{
    fun onOkBtnClicked(selectedFacilities: MutableList<DialogFacility>)
}