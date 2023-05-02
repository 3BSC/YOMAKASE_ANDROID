package com.example.yomakase.repository

import com.example.yomakase.RetrofitBuilder
import com.example.yomakase.api.JoinOwnerMemberApi
import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberReq

class JoinOwnerMemberRepository {

    private val client = RetrofitBuilder.getInstance().create(JoinOwnerMemberApi::class.java)

    suspend fun isEmailDuplicated(email: String) = client.reqEmailDuplicatedCheck(email)

    suspend fun isNicknameDuplicated(nickname: String) = client.reqNicknameDuplicatedCheck(nickname)

    suspend fun reqJoinOwnerMember(joinOwnerMemberReq: JoinOwnerMemberReq) = client.reqJoinGeneralMember(joinOwnerMemberReq)
}