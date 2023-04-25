package com.example.yomakase.api

import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberReq
import com.example.yomakase.model.retrofit.join_general_member.JoinGeneralMemberResult
import retrofit2.http.POST

interface JoinGeneralMemberApi {
    @POST("user/register")
    suspend fun reqJoinGeneralMember(joinGeneralMemberReq: JoinGeneralMemberReq): JoinGeneralMemberResult

    @POST("user/emailDupCheck")
    suspend fun reqEmailDuplicatedCheck(email: String): Boolean

    @POST("user/nicknameDupCheck")
    suspend fun reqNicknameDuplicatedCheck(nickname: String): Boolean
}