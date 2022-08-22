package com.example.storeRental.dto

import java.time.LocalDate

data class RentalDetailDto (
    val rentalId:Long,
    val storeId:Long,
    val unitPrice:Double,
    val floor:String,
    val createdDate:LocalDate,
    val Id:Long
    )