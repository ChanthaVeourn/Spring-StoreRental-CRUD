package com.example.storeRental.dto

import java.time.LocalDate

data class RentalDto(
    val id: Long? = null,
    val cus_id: Long? = null,
    val created: LocalDate? = null,
    val updated: LocalDate? = null
)