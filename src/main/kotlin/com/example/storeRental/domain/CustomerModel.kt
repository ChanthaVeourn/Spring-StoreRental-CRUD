package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "customer")
class CustomerModel(
    var name:String,
    var address:String?,
    var phone: String,
    ):BaseModel(){
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "customer")
    lateinit var rentals: MutableList<RentalModel>
}