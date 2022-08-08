package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue.Value.LocalClass

@Entity
@Table(name = "rental")
class RentalModel(

    @ManyToOne(fetch = FetchType.LAZY,optional = false, cascade = [CascadeType.DETACH])
    @JoinColumn(name = "CusId", referencedColumnName = "id")
    var customer: CustomerModel
):BaseModel(){

    @Column(name = "rentDate")
    override var createdDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm"))

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rental", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var rentalDetails:MutableList<RentalDetailModel>
}