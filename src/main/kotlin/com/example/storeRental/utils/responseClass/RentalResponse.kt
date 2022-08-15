package com.example.storeRental.utils.responseClass

import java.time.LocalDate

data class RentalResponse(
    val id: Long?,
    val cus_id: Long?,
    val created: LocalDate?,
    val updated: LocalDate?
)