package com.example.yomakase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.yomakase.App
import com.example.yomakase.R
import com.example.yomakase.databinding.ActivityLoginBinding
import com.example.yomakase.model.retrofit.login.LoginReq
import com.example.yomakase.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    init {
        /*
        val auto = App.prefs.autoLogin
        if auto
            val ref = pref.get(refreshToken)
            ref로 accessToken 발급 api call
                if response is success
                    to MainActivity
                    new Token 저장
                else
                    리프레쉬도 만료된거임
                    로그인 다시 해야됨
                    Toast(로그인 정보가 만료되었습니다. 다시 로그인해주세요.)
         */

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG", "onCreate: ")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.result.observe(this, Observer {
            /*
            it.accessToken == id pw 에러
                Toast(계정 정보가 잘못 되었습니다.)
            if it.accessToken == 네트워크 에러
                Toast(로그인에 실패했습니다(네트워크 에러))
            if 정상{
                if auto
                    App.prefs.autoLogin = true
                else
                    App.prefs.autoLogin = false
                startActivity(MainActivity::class.java)
            }
             */
        })

    }

    fun onBtnLoginClicked(view: View){
        val email = binding.etEmail.text
        val pw = binding.etPassword.text
        val autoLogin = binding.cbAutoLogin.isChecked
        if (email.isEmpty() || pw.isEmpty())
            Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        else
            viewModel.reqLogin(LoginReq(email.toString(), pw.toString(), autoLogin))
    }

    fun onBtnJoinClicked(view: View){
        val intent = Intent(this, JoinOptionActivity::class.java)
        startActivity(intent)
    }

    fun onBtnFindAccountClicked(view: View){
        val intent = Intent(this, FindAccountActivity::class.java)
        startActivity(intent)
    }
}