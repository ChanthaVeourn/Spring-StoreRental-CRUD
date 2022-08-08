package com.example.storeRental.repo

import com.example.storeRental.domain.RentalDetailModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalDetailRepo: JpaRepository<RentalDetailModel, Long> {
}