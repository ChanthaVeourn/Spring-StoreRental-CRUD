package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Customer(
    @Column(nullable = false, unique = true, length = 32)
    var name:String,
    @Column(nullable = true, length = 128)
    var address:String?,
    @Column(nullable = false, unique = true, length = 16)
    var phone: String,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "customer")
    var rentals: MutableList<Rental>?=null
    ):BaseModel()