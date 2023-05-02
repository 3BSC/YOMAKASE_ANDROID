package com.example.yomakase.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.yomakase.R
import com.example.yomakase.databinding.ActivityJoinGeneralMemberBinding
import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberReq
import com.example.yomakase.viewmodel.JoinGeneralMemberViewModel
import java.text.SimpleDateFormat
import java.util.*

class JoinGeneralMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinGeneralMemberBinding
    private lateinit var viewModel: JoinGeneralMemberViewModel
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var pwVisible = false

    private val emailRegex = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$")
    private val phoneRegex = Regex("-?\\d+(\\.\\d+)?")

    private var email : String? = null
    private var nickname: String? = null
    private var birth: String? = null

    private val cal: Calendar = Calendar.getInstance()
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        birth = dateFormat.format(Date(year- 1900, month, dayOfMonth))
        binding.tvBirth.text = birth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_general_member)
        viewModel = ViewModelProvider(this)[JoinGeneralMemberViewModel::class.java]
        setUp()

        viewModel.emailResult.observe(this, androidx.lifecycle.Observer {
            /*
            //유효한 이메일
            if(it.validated)
                email = it.email
            else
                email = null
             */
        })

        viewModel.nicknameResult.observe(this, androidx.lifecycle.Observer {
            /*
            //유효한 닉네임
            if(it.validated)
                nickname = it.nickname
            else
                nickname = null
             */
        })

        viewModel.joinGeneralMemberResult.observe(this, androidx.lifecycle.Observer {
            /*
            if 회원가입 성공{
                1. sharedPreference 에 Token 값들 저장
                2. sharedPreference 에 User data 저장
                3. to Main Activity
            }
            else{
                Toast 네트워크 에러
            }
             */
        })
    }

    private fun setUp(){
        setupSpinnerEmailDomain()
        setupSpinnerHandler()
        setTodayDate()
    }

    fun emailValidator(view: View){
        if (binding.etEmail.text.isEmpty())
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        else {
            var id = binding.etEmail.text.toString()
            val domain = binding.spEmailDomain.selectedItem.toString()
            if (domain != "직접입력")
                id += domain

            if (!id.matches(emailRegex))
                Toast.makeText(this, "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            //else
                //viewModel.emailDuplicatedCheck(id)
        }
    }

    fun nicknameValidator(){
        val nickname = binding.etNickname.text.toString()
        if (nickname.isEmpty())
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
        else{
            //viewModel.nicknameDuplicatedCheck(nickname)
        }
    }

    private fun phoneValidator(): Boolean{
        val phone = binding.etPhoneNumber.text.toString()
        if (phone.isEmpty()){
            Toast.makeText(this, "핸드폰번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!phone.matches(phoneRegex)){
            Toast.makeText(this, "번호는 -를 빼고 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun passwordValidator(): Boolean{
        val password = binding.etPassword.text
        val passwordCheck = binding.etPasswordCheck.text.toString()

        //null 확인
        if (password.isEmpty() || passwordCheck.isEmpty()) {
            Toast.makeText(this, "비밀번호와 비밀번호 확인란을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            //정규표현식 확인
            if (!password.toString().matches(passwordRegex)) {
                Toast.makeText(
                    this,
                    "영어, 숫자, 특수기호를 포함한 8자리 이상의 비밀번호를 설정해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            else{
                if (password.toString() != passwordCheck) {
                    Toast.makeText(this, "비밀번호와 비밀번화 확인란이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }

    fun reqJoinGeneralMember(view: View){
        if (email == null)
            Toast.makeText(this, "이메일 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
        else if(nickname == null)
            Toast.makeText(this, "닉네임 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
        else if(birth == null)
            Toast.makeText(this, "생일을 선택해주세요.", Toast.LENGTH_SHORT).show()
        else if(passwordValidator() && phoneValidator()){
            Toast.makeText(this,"성공", Toast.LENGTH_SHORT).show()
            /*
            val joinGeneralMemberReq = JoinGeneralMemberReq(
                email!!,
                binding.etPassword.text.toString(),
                nickname!!,
                birth!!,
                binding.etPhoneNumber.text.toString()
            )
            viewModel.reqJoinGeneralMember(joinGeneralMemberReq)
             */
        }
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
    }

    private fun setTodayDate(){
        binding.tvBirth.text = dateFormat.format(Date(System.currentTimeMillis()))
    }

    fun datePicker(view: View){
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