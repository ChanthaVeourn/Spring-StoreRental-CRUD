package com.example.storeRental.utils.dto

import java.time.LocalDate

data class StoreUpdateImgDto (
    val id:Long?,
    val imgUrl:String?,
    val updated:LocalDate?
    )