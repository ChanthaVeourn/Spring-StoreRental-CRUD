package com.example.storeRental.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*


@Entity
@Table(name = "StoreImage")
class StoreImageModel(

    @Column(nullable = false, length = 128)
    var imgUrl: String,

    @OneToOne(cascade = [CascadeType.DETACH], optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    var store:StoreModel? = null

):BaseModel(){
    override var createdDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
}