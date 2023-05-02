package com.example.yomakase.model.retrofit.join_owner_member

import com.example.yomakase.model.rv_item.ClosedDay
import com.example.yomakase.model.rv_item.Facility
import com.example.yomakase.model.rv_item.Price
import java.io.File

data class JoinOwnerMemberReq(
    val email: String,
    val password: String,
    val nickname: String,
    val birthday: String,
    val phone: String,
    val storeName: String,
    val storeTel: String,
    val registration: File,
    val category: String,
    val mainAddress: String,
    val sunAddress: String,
    val prices: List<Price>?,
    val service_start: String?,
    val service_end: String?,
    val break_start: String?,
    val break_end: String?,
    val closedDay: List<ClosedDay>?,
    val facilities: List<Facility>?,
    val summary: String?
)
