package com.example.yomakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberReq
import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberResult
import com.example.yomakase.repository.JoinGeneralMemberRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class JoinGeneralMemberViewModel: ViewModel() {
    private val repository = JoinGeneralMemberRepository()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //네트워크 에러 -> Toast 로그인에 실패했습니다.
    }

    private val _emailResult = MutableLiveData<Boolean>()
    val emailResult : LiveData<Boolean>
        get() = _emailResult

    private val _nicknameResult = MutableLiveData<Boolean>()
    val nicknameResult : LiveData<Boolean>
        get() = _nicknameResult

    private val _joinGeneralMemberResult = MutableLiveData<JoinGeneralMemberResult>()
    val joinGeneralMemberResult : LiveData<JoinGeneralMemberResult>
        get() = _joinGeneralMemberResult
    fun emailDuplicatedCheck(email: String) = viewModelScope.launch(exceptionHandler) {
        _emailResult.value = repository.isEmailDuplicated(email)
    }

    fun nicknameDuplicatedCheck(nickname: String) = viewModelScope.launch(exceptionHandler) {
        _nicknameResult.value = repository.isNicknameDuplicated(nickname)
    }

    fun reqJoinGeneralMember(joinGeneralMemberReq: JoinGeneralMemberReq) = viewModelScope.launch(exceptionHandler) {
        _joinGeneralMemberResult.value = repository.reqJoinGeneralMember(joinGeneralMemberReq)
    }
}