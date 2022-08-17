package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
class RentalDetail(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(optional = false, cascade = [CascadeType.DETACH], fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", referencedColumnName = "id")
    var store: Store,
    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(optional = false, cascade = [CascadeType.DETACH], fetch = FetchType.LAZY)
    @JoinColumn(name= "rentalId", referencedColumnName = "id")
    var rental: Rental,
    var description:String? = null,
    var unitPrice:Double = store.unitPrice,
    var storeType: String = store.storeType.typeName,
    var floor:String = store.floor
):BaseModel()