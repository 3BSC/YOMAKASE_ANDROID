package com.example.yomakase.model.rv_item

data class OwnerReservation(
    val reservationNumber: Int,
    val customerName: String,
    val customerPhone: String,
    val reservationDate: String,
    val people: Int
    )
