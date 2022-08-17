package com.example.storeRental.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*


@Entity
class StoreImage(

    @Column(nullable = false, length = 128)
    var imgUrl: String,

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    var store:Store? = null

):BaseModel()