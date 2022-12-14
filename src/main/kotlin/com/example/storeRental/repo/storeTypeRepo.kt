package com.example.storeRental.repo

import com.example.storeRental.domain.StoreType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreTypeRepo: JpaRepository<StoreType, Long> {
}