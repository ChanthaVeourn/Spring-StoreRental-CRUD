package com.example.storeRental.utils.requestClass

data class StoreCreateRequest (
    val typeId:Long,
    val imgUrl:String? = null,
    val unitPrice:Double,
    val floor:String
    )