package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
class RentalDetail(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_store_id"))
    var store: Store,
    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name= "rentalId", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_rental_id"))
    var rental: Rental,
    var description:String? = null,
    var unitPrice:Double = store.unitPrice,
    var storeType: String = store.storeType.typeName,
    var floor:String = store.floor
):BaseModel()