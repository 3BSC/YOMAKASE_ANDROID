package com.example.yomakase.model.retrofit.login

data class LoginReq(
    val email: String,
    val password: String,
    val autoLogin: Boolean
)
