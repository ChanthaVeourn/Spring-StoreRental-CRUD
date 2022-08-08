package com.example.storeRental.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*


@Entity
@Table(name = "StoreImage")
class StoreImageModel(
    @Column(length = 128)
    var imgUrl: String,
    @OneToOne(optional = false, cascade = [ CascadeType.PERSIST])
    @MapsId
    @JoinColumn(name = "storeId", referencedColumnName = "id")
    var store:StoreModel
){
    @Id
    var  id: Long? = null
    var createdDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
}