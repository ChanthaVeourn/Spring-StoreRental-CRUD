package com.example.storeRental.repo

import com.example.storeRental.domain.RentalDetailModel
import com.example.storeRental.utils.projection.RentalDetailProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalDetailRepo: JpaRepository<RentalDetailModel, Long> {
    fun findByRentalId(id:Long):List<RentalDetailProjection>
    fun findByStoreId(id:Long):List<RentalDetailProjection>
}