package com.example.storeRental.dto

import java.time.LocalDate

data class StoreUpdateImgDto (
    val id:Long?,
    val imgUrl:String?,
    val updated:LocalDate?
    )