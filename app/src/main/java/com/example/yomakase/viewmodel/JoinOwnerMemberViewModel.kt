package com.example.yomakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberReq
import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberResult
import com.example.yomakase.repository.JoinOwnerMemberRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class JoinOwnerMemberViewModel: ViewModel() {
    private val repository = JoinOwnerMemberRepository()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //네트워크 에러 -> Toast 로그인에 실패했습니다.
    }

    private val _emailResult = MutableLiveData<Boolean>()
    val emailResult : LiveData<Boolean>
        get() = _emailResult

    private val _nicknameResult = MutableLiveData<Boolean>()
    val nicknameResult : LiveData<Boolean>
        get() = _nicknameResult

    private val _joinOwnerMemberResult = MutableLiveData<JoinOwnerMemberResult>()
    val joinGeneralMemberResult : LiveData<JoinOwnerMemberResult>
        get() = _joinOwnerMemberResult

    fun emailDuplicatedCheck(email: String) = viewModelScope.launch(exceptionHandler) {
        _emailResult.value = repository.isEmailDuplicated(email)
    }

    fun nicknameDuplicatedCheck(nickname: String) = viewModelScope.launch(exceptionHandler) {
        _nicknameResult.value = repository.isNicknameDuplicated(nickname)
    }

    fun reqJoinGeneralMember(joinOwnerMemberReq: JoinOwnerMemberReq) = viewModelScope.launch(exceptionHandler) {
        _joinOwnerMemberResult.value = repository.reqJoinOwnerMember(joinOwnerMemberReq)
    }
}