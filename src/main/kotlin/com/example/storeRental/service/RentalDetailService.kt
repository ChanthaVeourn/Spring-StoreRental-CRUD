package com.example.storeRental.service

import com.example.storeRental.domain.RentalDetailModel
import com.example.storeRental.repo.RentalDetailRepo
import org.springframework.stereotype.Service

@Service
class RentalDetailService(private val rentalDetailRepo: RentalDetailRepo):BaseSevice<RentalDetailModel, Long>{

    override fun save(model: RentalDetailModel) {

    }

    override fun getById(id: Long): RentalDetailModel {
        return rentalDetailRepo.findById(id).orElse(null)
    }

    override fun deleteById(id: Long) {

    }

}