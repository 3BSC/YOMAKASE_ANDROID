package com.example.yomakase.model.retrofit.issue_token

data class IssueNewTokenResult(
    val newAccessToken: String,
    val newRefreshToken: String
)
