package com.example.yomakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomakase.model.retrofit.find_account.FindEmailReq
import com.example.yomakase.model.retrofit.find_account.FindEmailResult
import com.example.yomakase.model.retrofit.find_account.FindPasswordReq
import com.example.yomakase.model.retrofit.find_account.FindPasswordResult
import com.example.yomakase.model.retrofit.login.LoginResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class FindAccountViewModel: ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //네트워크 에러 -> Toast 로그인에 실패했습니다.
    }

    private val _email = MutableLiveData<FindEmailResult>()
    val email : LiveData<FindEmailResult>
        get() = _email

    private val _password = MutableLiveData<FindPasswordResult>()
    val password : LiveData<FindPasswordResult>
        get() = _password

    fun reqFindEmail(findEmailReq: FindEmailReq) = viewModelScope.launch(exceptionHandler) {

    }

    fun reqFindPassword(findPasswordReq: FindPasswordReq) = viewModelScope.launch(exceptionHandler) {

    }
}