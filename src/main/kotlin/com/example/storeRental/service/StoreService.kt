package com.example.storeRental.service

import com.example.storeRental.domain.StoreImageModel
import com.example.storeRental.domain.StoreModel
import com.example.storeRental.repo.StoreImageRepo
import com.example.storeRental.repo.StoreRepo
import org.springframework.stereotype.Service

@Service
class StoreService(private val storeImageRepo: StoreImageRepo,
                   private val storeRepo: StoreRepo):BaseSevice<StoreModel, Long> {

    override fun save(model: StoreModel) {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): StoreModel {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    fun setImage(storeId:Long, imgUrl:String){
        val store = storeRepo.findById(storeId).orElse(null)
        val newImg = StoreImageModel(imgUrl,store)
        storeImageRepo.save(newImg)
    }
}