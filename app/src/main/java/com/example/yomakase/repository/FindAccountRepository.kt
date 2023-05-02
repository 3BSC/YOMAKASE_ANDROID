package com.example.yomakase.repository

import com.example.yomakase.RetrofitBuilder
import com.example.yomakase.api.FindAccountApi
import com.example.yomakase.model.retrofit.find_account.FindEmailReq
import com.example.yomakase.model.retrofit.find_account.FindPasswordReq

class FindAccountRepository {

    private val client = RetrofitBuilder.getInstance().create(FindAccountApi::class.java)

    suspend fun reqFindEmail(findEmailReq: FindEmailReq) = client.reqFindEmail(findEmailReq)

    suspend fun reqFindPassword(findPasswordReq: FindPasswordReq) = client.reqFindPassword(findPasswordReq)
}