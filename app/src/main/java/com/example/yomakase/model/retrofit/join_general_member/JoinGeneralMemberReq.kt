package com.example.yomakase.model.retrofit.join_general_member

import java.sql.Date

data class JoinGeneralMemberReq(
    val email: String,
    val password: String,
    val nickname: String,
    val birthday: Date,
    val phone: String
)
