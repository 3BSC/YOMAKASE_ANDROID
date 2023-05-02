package com.example.yomakase.api

import com.example.yomakase.model.retrofit.login.LoginReq
import com.example.yomakase.model.retrofit.login.LoginResult
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("user/login")
    suspend fun reqLogin(@Body loginReq: LoginReq): LoginResult
}