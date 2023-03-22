package com.example.yomakase

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.databinding.ActivityJoinGeneralMemberBinding
import com.example.yomakase.databinding.ActivityJoinOwnerMemberBinding
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class JoinOwnerMemberActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinOwnerMemberBinding
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var pwVisible = false
    private lateinit var priceList : MutableList<Price>
    private lateinit var priceAdapter: PriceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_owner_member)

        setUpPriceRv()
        setupSpinnerCategory()
        setupSpinnerEmailDomain()
        setupSpinnerPrice()
        setupSpinnerHandler()
        setTodayDate()
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
        priceList.add(Price("주말", "디너", 50000))
        priceList.add(Price("평일", "디너", 34000))
        priceAdapter = PriceAdapter(priceList, onClickRemoveBtn = {removePrice(it)})

        binding.rvPrice.apply {
            adapter = priceAdapter
            layoutManager = LinearLayoutManager(applicationContext)
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

}