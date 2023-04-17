package com.example.yomakase.repository

import com.example.yomakase.RetrofitBuilder
import com.example.yomakase.api.IssueNewTokenApi
import com.example.yomakase.api.LoginApi
import com.example.yomakase.model.retrofit.issue_token.IssueNewAccessTokenReq
import com.example.yomakase.model.retrofit.login.LoginReq

class LoginRepository {

    private val client = RetrofitBuilder.getInstance().create(LoginApi::class.java)
    private val newTokenClient = RetrofitBuilder.getInstance().create(IssueNewTokenApi::class.java)

    suspend fun reqLogin(loginReq: LoginReq) = client.reqLogin(loginReq)

    suspend fun issueNewToken(issueNewAccessTokenReq: IssueNewAccessTokenReq) = newTokenClient.issueNewToken(issueNewAccessTokenReq)
}