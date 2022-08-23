package com.example.storeRental.dto

import java.time.LocalDate

data class RentalDetailDto (
    val rentalId:Long? = null,
    val storeId:Long? = null,
    val unitPrice:Double? = null,
    val floor:String? = null,
    val createdDate:LocalDate? = null,
    val Id:Long? = null
    )