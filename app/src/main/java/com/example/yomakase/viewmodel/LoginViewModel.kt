package com.example.yomakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomakase.model.retrofit.issue_token.IssueNewAccessTokenReq
import com.example.yomakase.model.retrofit.login.LoginReq
import com.example.yomakase.model.retrofit.login.LoginResult
import com.example.yomakase.repository.LoginRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){
    private val repository = LoginRepository()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _result.value = LoginResult("err", "err")
        //access token 만료 err -> refresh token을 이용해 access token 재발급 api request
        //아이디 비밀번호가 일치 하지 않음 -> Toast 아이디 또는 비밀번호가 잘못되었습니다.
        //네트워크 에러 -> Toast 로그인에 실패했습니다.
    }

    private val _result = MutableLiveData<LoginResult>()
    val result : LiveData<LoginResult>
        get() = _result

    fun reqLogin(loginReq: LoginReq) = viewModelScope.launch(exceptionHandler) {
        _result.value = repository.reqLogin(loginReq)
    }

    fun issueNewToken(issueNewAccessTokenReq: IssueNewAccessTokenReq) = viewModelScope.launch(exceptionHandler) {
        val issueNewTokenResult = repository.issueNewToken(issueNewAccessTokenReq)

    }
}