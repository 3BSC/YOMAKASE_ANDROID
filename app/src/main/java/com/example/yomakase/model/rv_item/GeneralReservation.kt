package com.example.yomakase.model.rv_item

data class GeneralReservation(
    val reservationNumber: Int,
    val reservationStore: String,
    val reservationDate: String,
    val people: Int
)
