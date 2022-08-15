package com.example.storeRental.repo

import com.example.storeRental.domain.RentalModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalRepo: JpaRepository<RentalModel, Long> {
    fun findRentalByCustomerId(cusId: Long) : List<RentalModel>
    fun removeByCustomerId(cusId:Long)
}