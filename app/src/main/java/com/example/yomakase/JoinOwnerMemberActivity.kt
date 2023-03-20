package com.example.yomakase

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.yomakase.databinding.ActivityJoinGeneralMemberBinding
import com.example.yomakase.databinding.ActivityJoinOwnerMemberBinding
import java.text.SimpleDateFormat
import java.util.*

class JoinOwnerMemberActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinOwnerMemberBinding
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var pwVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_owner_member)
        setupSpinnerCategory()
        setupSpinnerEmailDomain()
        setupSpinnerPrice()
        setupSpinnerHandler()
        setTodayDate()
    }

    private fun setupSpinnerPrice() {
        val priceDays = resources.getStringArray(R.array.sp_price_day)
        val priceTimes = resources.getStringArray(R.array.sp_price_time)
        val priceDayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceDays)
        val priceTimeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceTimes)
        priceDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        priceTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPriceDay.adapter = priceDayAdapter
        binding.spPriceTime.adapter = priceTimeAdapter
    }

    private fun setupSpinnerCategory() {
        val categories = resources.getStringArray(R.array.sp_category)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter
    }

    private fun setupSpinnerEmailDomain() {
        val domains = resources.getStringArray(R.array.sp_email_domain)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, domains)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

    fun dataPicker(view: View){
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvBirth.text = "$year-${month+1}-$dayOfMonth"
        }
        DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar
            ,dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
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
}