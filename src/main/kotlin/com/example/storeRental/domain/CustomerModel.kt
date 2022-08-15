package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "customer")
class CustomerModel(
    @Column(nullable = false, unique = true, length = 32)
    var name:String,
    @Column(nullable = true, length = 128)
    var address:String?,
    @Column(nullable = false, unique = true)
    var phone: String,
    ):BaseModel(){
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "customer")
    lateinit var rentals: MutableList<RentalModel>
}