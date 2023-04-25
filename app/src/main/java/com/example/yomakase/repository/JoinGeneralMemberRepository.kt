package com.example.yomakase.repository

import com.example.yomakase.RetrofitBuilder
import com.example.yomakase.api.JoinGeneralMemberApi
import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberReq

class JoinGeneralMemberRepository {

    private val client = RetrofitBuilder.getInstance().create(JoinGeneralMemberApi::class.java)

    suspend fun isEmailDuplicated(email: String) = client.reqEmailDuplicatedCheck(email)

    suspend fun isNicknameDuplicated(nickname: String) = client.reqEmailDuplicatedCheck(nickname)

    suspend fun reqJoinGeneralMember(joinGeneralMemberReq: JoinGeneralMemberReq) = client.reqJoinGeneralMember(joinGeneralMemberReq)
}