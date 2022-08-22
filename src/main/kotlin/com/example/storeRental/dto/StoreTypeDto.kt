package com.example.storeRental.dto

import java.time.LocalDate

data class StoreTypeDto(
    val id:Long?,
    val typeName:String?,
    val createDate: LocalDate?
)