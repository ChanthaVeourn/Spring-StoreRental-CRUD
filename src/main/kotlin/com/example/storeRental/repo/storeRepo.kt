package com.example.storeRental.repo

import com.example.storeRental.domain.StoreImageModel
import com.example.storeRental.domain.StoreModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepo: JpaRepository<StoreModel, Long> {
}