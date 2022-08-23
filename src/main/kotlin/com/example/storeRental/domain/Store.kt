package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class Store(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StoreTypeID", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_store_type_id"))
    var storeType: StoreType,

    @JsonIgnore
    @OneToOne(mappedBy = "store", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var img: StoreImage? = null,

    @Column(nullable = false)
    var unitPrice:Double,

    @Column(nullable = false, length = 16)
    @Size(max = 16)
    var floor:String,

    @Column(name = "rentedStatus")
    var rented: Boolean = false,

    @JsonIgnore
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var rentalDetail:MutableList<RentalDetail>? = null,

    var updatedDate:LocalDate? = null
    ):BaseModel()