package com.example.storeRental.dto

import java.time.LocalDate

data class RentalDto(
    val id: Long?,
    val cus_id: Long?,
    val created: LocalDate?,
    val updated: LocalDate?
)