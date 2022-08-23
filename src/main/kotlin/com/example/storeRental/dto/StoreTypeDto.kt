package com.example.storeRental.dto

import java.time.LocalDate

data class StoreTypeDto(
    val id:Long? = null,
    val typeName:String? = null,
    val createDate: LocalDate? = null
)