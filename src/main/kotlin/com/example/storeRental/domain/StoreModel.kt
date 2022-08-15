package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "store")
class StoreModel(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StoreTypeID", referencedColumnName = "id")
    var storeType: StoreTypeModel,

    @JsonIgnore
    @OneToOne( fetch = FetchType.LAZY, mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var img: StoreImageModel? = null,

    @Column(nullable = false)
    var unitPrice:Double,

    @Column(nullable = false, length = 16)
    var floor:String,

):BaseModel(){

    @Column(name = "rentedStatus")
    var rented: Boolean = false

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var rentalDetail:MutableList<RentalDetailModel>? = null
    var updatedDate:String? = null
}