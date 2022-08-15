package com.example.storeRental.utils.projection

import java.time.LocalDate

data class RentalDetailProjection (
    val rentalId:Long,
    val storeId:Long,
    val unitPrice:Double,
    val floor:String,
    val createdDate:LocalDate,
    val Id:Long
    )