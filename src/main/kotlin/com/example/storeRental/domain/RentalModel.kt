package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "rental")
class RentalModel(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CusId", referencedColumnName = "id")
    var customer: CustomerModel

):BaseModel(){

    @Column(name = "rentDate")
    override var createdDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm"))

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rental", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var rentalDetails:MutableList<RentalDetailModel>

    @Column(name = "updated_date")
    var updatedDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm"))

}