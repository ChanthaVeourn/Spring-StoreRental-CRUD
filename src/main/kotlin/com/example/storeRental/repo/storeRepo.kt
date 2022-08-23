package com.example.storeRental.repo

import com.example.storeRental.domain.Store
import com.example.storeRental.dto.StoreDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepo: JpaRepository<Store, Long> {
    fun findByRentedTrue():List<StoreDto>

    fun findStoresBy():List<StoreDto>

    fun findAllByImgIsNotNullOrderById():List<StoreDto>

    fun findAllByImgIsNull():List<StoreDto>

}