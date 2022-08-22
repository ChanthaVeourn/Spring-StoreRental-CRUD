package com.example.storeRental.repo

import com.example.storeRental.domain.RentalDetail
import com.example.storeRental.dto.RentalDetailDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalDetailRepo: JpaRepository<RentalDetail, Long> {
    fun findByRentalId(id:Long):List<RentalDetailDto>
    fun findByStoreId(id:Long):List<RentalDetailDto>
}