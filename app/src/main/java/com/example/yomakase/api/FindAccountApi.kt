package com.example.yomakase.api

import com.example.yomakase.model.retrofit.find_account.FindEmailReq
import com.example.yomakase.model.retrofit.find_account.FindEmailResult
import com.example.yomakase.model.retrofit.find_account.FindPasswordReq
import com.example.yomakase.model.retrofit.find_account.FindPasswordResult
import retrofit2.http.Body
import retrofit2.http.POST

interface FindAccountApi {

    @POST("url")
    suspend fun reqFindEmail(@Body findEmailReq: FindEmailReq): FindEmailResult

    @POST("url")
    suspend fun reqFindPassword(@Body findPasswordReq: FindPasswordReq): FindPasswordResult
}