package com.example.yomakase.api

import com.example.yomakase.model.retrofit.issue_token.IssueNewAccessTokenReq
import com.example.yomakase.model.retrofit.issue_token.IssueNewTokenResult
import retrofit2.http.Body
import retrofit2.http.POST

interface IssueNewTokenApi {
    @POST("/url")
    suspend fun issueNewToken(@Body refreshToken: IssueNewAccessTokenReq): IssueNewTokenResult
}