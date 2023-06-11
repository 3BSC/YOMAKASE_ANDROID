package com.example.yomakase.view.owner

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.FacilityDialog
import com.example.yomakase.FacilityDialogInterface
import com.example.yomakase.KakaoAddressFindActivity
import com.example.yomakase.R
import com.example.yomakase.adapter.ClosedDayAdapter
import com.example.yomakase.adapter.FacilityAdapter
import com.example.yomakase.adapter.OwnerEditStoreInfoCourseTimeAdapter
import com.example.yomakase.adapter.PriceAdapter
import com.example.yomakase.databinding.FragmentOwnerEditStoreInfoBinding
import com.example.yomakase.model.rv_item.ClosedDay
import com.example.yomakase.model.rv_item.DialogFacility
import com.example.yomakase.model.rv_item.Price
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class OwnerEditStoreInfoFragment : Fragment(), FacilityDialogInterface {
    private lateinit var binding: FragmentOwnerEditStoreInfoBinding
    private lateinit var activityContext: Context

    private var imageFile: File? = null

    private val phoneRegex = Regex("-?\\d+(\\.\\d+)?")

    private lateinit var priceList : MutableList<Price>
    private lateinit var closedDays: MutableList<ClosedDay>
    private lateinit var courseTime: ArrayList<String>
    private var selectedFacilities = mutableListOf<DialogFacility>()

    private lateinit var courseTimeAdapter: OwnerEditStoreInfoCourseTimeAdapter

    private lateinit var facilityDialog : FacilityDialog


    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermitted ->
            if (isPermitted){
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                getBusinessRegistrationLauncher.launch(intent)
            }else{
                Toast.makeText(activityContext, "갤러리 권한이 거부 되었습니다.", Toast.LENGTH_LONG).show()
            }
        }
    private val getBusinessRegistrationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    imageFile = File(getRealPathFromUri(it))
                }
                if (imageFile != null)
                    binding.tvBusinessRegistration.text = "첨부됨"
            }
        }

    private val getAddressLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                binding.tvMainAddress.text = result.data!!.getStringExtra("data")
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_edit_store_info, container, false)
        activityContext = requireContext()

        setupSpinnerCategory()
        setupSpinnerHandler()
        setupSpinnerPrice()
        setUpClosedDayRv()
        setUpFacilityRv()
        setUpPriceRv()
        setUpCourseTimeRv()

        facilityDialog = FacilityDialog(this, activityContext, selectedFacilities.toList()).apply { isCancelable = false }

        binding.btnGallery.setOnClickListener {
            openGallery()
        }
        binding.btnSearchAddress.setOnClickListener {
            addressFind()
        }

        binding.btnAddPrice.setOnClickListener {
            addPrice()
        }

        binding.tvOpeningHoursStart.setOnClickListener {
            timePicker(it)
        }
        binding.tvOpeningHoursEnd.setOnClickListener {
            timePicker(it)
        }
        binding.tvBreakTimeStart.setOnClickListener {
            timePicker(it)
        }
        binding.tvBreakTimeEnd.setOnClickListener {
            timePicker(it)
        }
        binding.btnAddFacilities.setOnClickListener {
            openFacilityDialog()
        }

        binding.tvCourseStartTime.setOnClickListener {
            timePicker(it)
        }

        binding.btnAddCourseTime.setOnClickListener {
            addCourseTime()
        }

        return binding.root
    }

    private fun storeTelValidator(): Boolean{
        val phone = binding.etStoreTel.text
        if (!phone.matches(phoneRegex))
            return false
        return true
    }

    private fun addressFind(){
        getAddressLauncher.launch(Intent(activityContext, KakaoAddressFindActivity::class.java))
    }

    private fun setupSpinnerPrice() {
        val priceDays = resources.getStringArray(R.array.sp_price_day)
        val priceTimes = resources.getStringArray(R.array.sp_price_time)
        val priceDayAdapter = ArrayAdapter(activityContext, R.layout.spinner_text, priceDays)
        val priceTimeAdapter = ArrayAdapter(activityContext, R.layout.spinner_text, priceTimes)
        priceDayAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        priceTimeAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spPriceDay.adapter = priceDayAdapter
        binding.spPriceTime.adapter = priceTimeAdapter
    }

    private fun setupSpinnerCategory() {
        val categories = resources.getStringArray(R.array.sp_category)
        val adapter = ArrayAdapter(activityContext, R.layout.spinner_text, categories)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spCategory.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("category", "onItemSelected: ${binding.spCategory.getItemAtPosition(p2)} ")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun timePicker(view : View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { time, hour, min ->
            val timeFormatted = "${hour.toString().padStart(2, '0')} : ${min.toString().padStart(2, '0')}"
            Log.d("TAG", "timePicker: $timeFormatted")
            when(view.id){
                R.id.tvOpeningHoursStart -> { binding.tvOpeningHoursStart.text = timeFormatted}
                R.id.tvOpeningHoursEnd -> {binding.tvOpeningHoursEnd.text = timeFormatted}
                R.id.tvBreakTimeStart -> {binding.tvBreakTimeStart.text = timeFormatted}
                R.id.tvBreakTimeEnd -> {binding.tvBreakTimeEnd.text = timeFormatted}
                R.id.tvCourseStartTime -> {binding.tvCourseStartTime.text =timeFormatted}
            }
        }

        TimePickerDialog(activityContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpPriceRv(){
        priceList = mutableListOf()
        val priceAdapter = PriceAdapter(priceList, onClickRemoveBtn = { removePrice(it)})

        binding.rvPrice.apply {
            adapter = priceAdapter
            layoutManager = LinearLayoutManager(activityContext)
        }
    }

    private fun setUpCourseTimeRv(){
        courseTime = arrayListOf()
        courseTimeAdapter = OwnerEditStoreInfoCourseTimeAdapter(courseTime)
        binding.rvCourseTime.apply {
            this.adapter = courseTimeAdapter
            this.layoutManager = LinearLayoutManager(activityContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addCourseTime(){
        courseTime.add(binding.tvCourseStartTime.text.toString())
        courseTimeAdapter.notifyDataSetChanged()
        binding.tvCourseStartTime.text = "시간을 선택해주세요"
    }

    private fun setUpClosedDayRv(){
        closedDays = mutableListOf()
        var closedDayList = mutableListOf(
            ClosedDay("일요일"), ClosedDay("월요일"), ClosedDay("화요일"), ClosedDay("수요일"),
            ClosedDay("목요일"), ClosedDay("금요일"), ClosedDay("토요일"), ClosedDay("공휴일")
        )
        var closedDayAdapter = ClosedDayAdapter(closedDayList, onClickItemListener = {it, position ->
            if(it.tag == "0"){
                it.setBackgroundResource(R.drawable.btn_login)
                it.tag = "1"
                closedDays.add(closedDayList[position])
            }else{
                it.setBackgroundResource(R.drawable.btn_unclicked)
                it.tag = "0"
                closedDays.remove(closedDayList[position])
            }
        })
        binding.rvClosedDays.apply {
            adapter = closedDayAdapter
            layoutManager = LinearLayoutManager(activityContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removePrice(price: Price) {
        priceList.remove(price)
        binding.rvPrice.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPrice(){
        priceList.add(
            Price(
                binding.spPriceDay.selectedItem.toString(),
                binding.spPriceTime.selectedItem.toString(),
                binding.etPrice.text.toString().toInt()
            )
        )
        binding.rvPrice.adapter?.notifyDataSetChanged()
    }

    private fun setUpFacilityRv(){
        binding.rvFacilities.apply {
            adapter = FacilityAdapter(selectedFacilities)
            layoutManager = LinearLayoutManager(activityContext, LinearLayoutManager.HORIZONTAL ,false)
        }
    }

    private fun openFacilityDialog(){
        facilityDialog.preSelected = selectedFacilities.toList()
        activity?.let { facilityDialog.show(it.supportFragmentManager, "Facility") }
    }

    override fun onOkBtnClicked(selected: MutableList<DialogFacility>) {
        selectedFacilities = selected
        setUpFacilityRv()
    }

    private fun getRealPathFromUri(uri: Uri): String{
        val buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) return uri.path!!

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri, proj, null, null, null)

        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun openGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        else
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}