package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
class Rental(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CusId", referencedColumnName = "id")
    var customer: Customer,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rental", cascade = [CascadeType.ALL], orphanRemoval = true)
    var rentalDetails:MutableList<RentalDetail>?=null,

    @Column(name = "updated_date")
    var updatedDate: LocalDate = LocalDate.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
        DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))

):BaseModel()