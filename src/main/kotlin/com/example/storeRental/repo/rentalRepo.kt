package com.example.storeRental.repo

import com.example.storeRental.domain.Rental
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RentalRepo: JpaRepository<Rental, Long> {
    fun findRentalByCustomerId(cusId: Long) : List<Rental>
    fun removeByCustomerId(cusId:Long)

    @Query("Select r.id from Rental r where r.customer.id = :cusId")
    fun getRentalCusId(cusId:Long):List<Long>?
}