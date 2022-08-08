package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "store")
class StoreModel(
    @ManyToOne
    @JoinColumn(name = "StoreTypeID", referencedColumnName = "id")
    var storeType: StoreTypeModel,

    @Column(nullable = false)
    var unitPrice:Double,

    @Column(nullable = false, length = 16)
    var floor:String,

    @OneToOne( mappedBy = "store", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var img: StoreImageModel? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var rentalDetail:MutableList<RentalDetailModel>? = null
):BaseModel(){
    override var id: Long?
        get() = super.id
        set(value) {}
    @Column(name = "rentedStatus")
    var rented: Boolean = false

}