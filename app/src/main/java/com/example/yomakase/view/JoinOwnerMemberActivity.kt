package com.example.yomakase.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.databinding.ActivityJoinOwnerMemberBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest
import android.widget.Toast
import com.example.yomakase.*
import com.example.yomakase.adapter.ClosedDayAdapter
import com.example.yomakase.adapter.FacilityAdapter
import com.example.yomakase.adapter.PriceAdapter
import com.example.yomakase.model.rv_item.ClosedDay
import com.example.yomakase.model.rv_item.DialogFacility
import com.example.yomakase.model.rv_item.Price

class JoinOwnerMemberActivity : AppCompatActivity(), FacilityDialogInterface {

    lateinit var binding: ActivityJoinOwnerMemberBinding
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var pwVisible = false
    private var imageFile: File? = null
    private lateinit var priceList : MutableList<Price>
    private lateinit var priceAdapter: PriceAdapter
    private lateinit var closedDays: MutableList<ClosedDay>
    private var selectedFacilities = mutableListOf<DialogFacility>()
    private var facilityDialog : FacilityDialog = FacilityDialog(this, this, selectedFacilities.toList()).apply { isCancelable = false }

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
                Toast.makeText(this, "갤러리 권한이 거부 되었습니다.", Toast.LENGTH_LONG).show()
            }
        }
    private val getBusinessRegistrationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    imageFile = File(getRealPathFromUri(it))
                }
                if (imageFile != null)
                    binding.tvBusinessRegistration.text = "첨부됨"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_owner_member)

        setUpClosedDayRv()
        setUpPriceRv()
        setupSpinnerCategory()
        setupSpinnerEmailDomain()
        setupSpinnerPrice()
        setupSpinnerHandler()
        setTodayDate()
        setUpFacilityRv()
    }

    private fun setupSpinnerPrice() {
        val priceDays = resources.getStringArray(R.array.sp_price_day)
        val priceTimes = resources.getStringArray(R.array.sp_price_time)
        val priceDayAdapter = ArrayAdapter(this, R.layout.spinner_text, priceDays)
        val priceTimeAdapter = ArrayAdapter(this, R.layout.spinner_text, priceTimes)
        priceDayAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        priceTimeAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spPriceDay.adapter = priceDayAdapter
        binding.spPriceTime.adapter = priceTimeAdapter
    }

    private fun setupSpinnerCategory() {
        val categories = resources.getStringArray(R.array.sp_category)
        val adapter = ArrayAdapter(this, R.layout.spinner_text, categories)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spCategory.adapter = adapter
    }

    private fun setupSpinnerEmailDomain() {
        val domains = resources.getStringArray(R.array.sp_email_domain)
        val adapter = ArrayAdapter(this, R.layout.spinner_text, domains)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spEmailDomain.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spEmailDomain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("email", "onItemSelected: ${binding.spEmailDomain.getItemAtPosition(position)}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("category", "onItemSelected: ${binding.spCategory.getItemAtPosition(p2)} ")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTodayDate(){
        binding.tvBirth.text = dateFormat.format(Date(System.currentTimeMillis()))
    }

    fun datePicker(view: View){
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvBirth.text = "$year-${month+1}-$dayOfMonth"
        }
        DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar
            ,dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    fun timePicker(view: View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener {time, hour, min ->
            val timeFormatted = "${hour.toString().padStart(2, '0')} : ${min.toString().padStart(2, '0')}"
            when(view.id){
                R.id.tvOpeningHoursStart -> {binding.tvOpeningHoursStart.text = timeFormatted}
                R.id.tvOpeningHoursEnd -> {binding.tvOpeningHoursEnd.text = timeFormatted}
                R.id.tvBreakTimeStart -> {binding.tvBreakTimeStart.text = timeFormatted}
                R.id.tvBreakTimeEnd -> {binding.tvBreakTimeEnd.text = timeFormatted}
            }
        }

        TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
    }

    fun visiblePassword(view: View){
        when(pwVisible){
            false -> {
                pwVisible = true
                binding.etPasswordCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnPasswordHide.setImageResource(R.drawable.ic_hide)
            }
            true -> {
                pwVisible = false
                binding.etPasswordCheck.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnPasswordHide.setImageResource(R.drawable.ic_show)
            }
        }
        binding.etPasswordCheck.setTextAppearance(R.style.join_text)
        binding.etPasswordCheck.setSelection(binding.etPasswordCheck.text!!.length)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpPriceRv(){
        priceList = mutableListOf()
        priceAdapter = PriceAdapter(priceList, onClickRemoveBtn = { removePrice(it)})

        binding.rvPrice.apply {
            adapter = priceAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
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
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removePrice(price: Price) {
        priceList.remove(price)
        binding.rvPrice.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPrice(view: View){
        priceList.add(
            Price(
                binding.spPriceDay.selectedItem.toString(),
                binding.spPriceTime.selectedItem.toString(),
                binding.etPrice.text.toString().toInt()
            )
        )
        priceAdapter.notifyDataSetChanged()
    }

    private fun setUpFacilityRv(){
        binding.rvFacilities.apply {
            adapter = FacilityAdapter(selectedFacilities)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL ,false)
        }
    }

    fun openFacilityDialog(view: View){
        facilityDialog.preSelected = selectedFacilities.toList()
        facilityDialog.show(this.supportFragmentManager, "Facility")
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
        val cursor = contentResolver.query(uri, proj, null, null, null)

        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    fun openGallery(view: View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        else
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}