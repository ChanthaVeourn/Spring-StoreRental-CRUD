package com.example.storeRental.domain

import org.springframework.lang.Nullable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "rentalDetail")
class RentalDetailModel(

    @ManyToOne(optional = false, cascade = [CascadeType.DETACH], fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", referencedColumnName = "id")
    var store: StoreModel,

    @ManyToOne(optional = false, cascade = [CascadeType.DETACH], fetch = FetchType.LAZY)
    @JoinColumn(name= "rentalId", referencedColumnName = "id")
    var rental: RentalModel
):BaseModel(){
    var description:String? = null
    var unitPrice:Double = store.unitPrice
    var storeType: String = store.storeType.typeName
    var floor:String = store.floor
}