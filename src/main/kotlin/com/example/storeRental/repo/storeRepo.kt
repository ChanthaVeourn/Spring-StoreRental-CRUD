package com.example.storeRental.repo

import com.example.storeRental.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepo: JpaRepository<Store, Long> {
    fun findByRentedTrue():List<Store>

}