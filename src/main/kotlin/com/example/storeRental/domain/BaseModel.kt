package com.example.storeRental.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseModel (
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name = "createdDate")
    var createdDate: LocalDate = LocalDate.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
        DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    )
}
