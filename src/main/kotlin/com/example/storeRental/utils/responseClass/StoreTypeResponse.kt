package com.example.storeRental.utils.responseClass

import java.time.LocalDate
import java.time.LocalDateTime

data class StoreTypeResponse(
    val id:Long?,
    val typeName:String?,
    val createDate: LocalDate?
)