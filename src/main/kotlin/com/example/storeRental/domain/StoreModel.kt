package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "store")
class StoreModel(

    @ManyToOne
    @JoinColumn(name = "StoreTypeID", referencedColumnName = "id")
    var storeType: StoreTypeModel,

    @OneToOne( mappedBy = "store")
    var img: StoreImageModel? = null,

    @Column(nullable = false)
    var unitPrice:Double,

    @Column(nullable = false, length = 16)
    var floor:String,

):BaseModel(){

    @Column(name = "rentedStatus")
    var rented: Boolean = false

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var rentalDetail:MutableList<RentalDetailModel>? = null
}