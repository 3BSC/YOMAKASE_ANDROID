package com.example.yomakase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.yomakase.R
import com.example.yomakase.databinding.ActivityFindAccountBinding
import com.example.yomakase.model.retrofit.find_account.FindEmailReq
import com.example.yomakase.model.retrofit.find_account.FindPasswordReq
import com.example.yomakase.viewmodel.FindAccountViewModel

class FindAccountActivity : AppCompatActivity() {

    private val emailRegex = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private val phoneRegex = Regex("-?\\d+(\\.\\d+)?")

    private lateinit var binding: ActivityFindAccountBinding
    private lateinit var viewModel: FindAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.email.observe(this, Observer {
            //Dialog 띄우기
        })

        viewModel.password.observe(this, Observer {
            //Dialog 띄우기
        })
    }

    fun reqFindEmail(view: View){
        val phone = binding.etEmailFind.text
        if (phoneValidator(phone)) {
            viewModel.reqFindEmail(
                FindEmailReq(phone.toString())
            )
        }
    }

    fun reqFindPassword(view: View){
        val email = binding.etPasswordFind.text
        if (emailValidator(email)){
            viewModel.reqFindPassword(
                FindPasswordReq(email.toString())
            )
        }
    }

    private fun phoneValidator(phone: Editable): Boolean{
        if (phone.isEmpty()){
            Toast.makeText(this, "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }else if (!phone.toString().matches(phoneRegex)){
            Toast.makeText(this, "핸드폰 번호는 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun emailValidator(email: Editable): Boolean{
        if (email.isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }else if(!email.toString().matches(emailRegex)){
            Toast.makeText(this, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}