package com.example.storeRental.utils.requestClass

import org.springframework.lang.NonNull

data class StoreUpdateRequest (
    @NonNull
    val id:Long,
    val unitPrice:Double?,
    val floor:String?,
    val rented:Boolean?
    )