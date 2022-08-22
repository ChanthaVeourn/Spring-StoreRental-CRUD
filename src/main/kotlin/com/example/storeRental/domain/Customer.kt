package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_name_phone", columnList = "name, phone")])
class Customer(
    @Column(nullable = false, unique = true, length = 32)
    var name:String,
    @Column(nullable = true, length = 128)
    var address:String?,
    @Column(nullable = false, unique = true, length = 16)
    var phone: String,
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    var rentals: MutableList<Rental> = mutableListOf()
    ):BaseModel()