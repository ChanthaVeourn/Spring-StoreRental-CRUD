package com.example.storeRental.utils.requestClass

import org.springframework.lang.NonNull

data class StoreTypeUpdateRequest (
    @NonNull
    val id:Long,
    @NonNull
    val typeName:String
)