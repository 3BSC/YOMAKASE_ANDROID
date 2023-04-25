package com.example.yomakase.api

import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberReq
import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberResult
import retrofit2.http.POST

interface JoinOwnerMemberApi {
    @POST("user/register")
    suspend fun reqJoinGeneralMember(joinGeneralMemberReq: JoinOwnerMemberReq): JoinOwnerMemberResult

    @POST("user/emailDupCheck")
    suspend fun reqEmailDuplicatedCheck(email: String): Boolean

    @POST("user/nicknameDupCheck")
    suspend fun reqNicknameDuplicatedCheck(nickname: String): Boolean
}