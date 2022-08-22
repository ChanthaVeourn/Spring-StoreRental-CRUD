package com.example.storeRental.utils.responseClass

data class ResponseData(
        val statusCode:Int?=null,
        val errorCode:String?=null,
        val resType:String?=null,
        val message:String?=null,
        val data:Any?=null
        )
