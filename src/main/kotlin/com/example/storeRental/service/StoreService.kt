package com.example.storeRental.service

import com.example.storeRental.utils.RequestValidation.StoreDataValidation
import com.example.storeRental.domain.StoreImageModel
import com.example.storeRental.domain.StoreModel
import com.example.storeRental.repo.StoreImageRepo
import com.example.storeRental.repo.StoreRepo
import com.example.storeRental.utils.requestClass.StoreUpdateRequest
import com.example.storeRental.utils.responseClass.StoreResponse
import com.example.storeRental.utils.responseClass.StoreUpdateImgResponse
import com.example.storeRental.utils.responseClass.StoreUpdateResponse
import org.apache.catalina.Store
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class StoreService(private val storeImageRepo: StoreImageRepo,
                   private val storeRepo: StoreRepo,
                    private val storeTypeService: StoreTypeService):BaseSevice<StoreModel, Long>{

    override fun save(model: StoreModel) {
        storeRepo.save(model)
    }

    override fun getById(id: Long): StoreModel {
        return storeRepo.findById(id).orElse(null)
    }

    fun getAll():List<StoreModel>{
        return storeRepo.findAll()
    }

    override fun deleteById(id: Long) {
        storeRepo.deleteById(id)
    }

    fun setImage(storeId:Long, imgUrl:String){
        val store = storeRepo.findById(storeId).orElse(null)
        val newImg = StoreImageModel(imgUrl,store)
        storeImageRepo.save(newImg)
    }

    fun updateImage(storeId:Long, imgUrl:String):StoreUpdateImgResponse?{
        val store = getById(storeId)
        val oldImg = store.img
        if(store != null){
            store.img = null
            store.updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
            storeImageRepo.delete(oldImg!!)
            val newStoreImg = StoreImageModel(imgUrl, store)
            storeImageRepo.save(newStoreImg)
            return StoreUpdateImgResponse(storeId, imgUrl, store.updatedDate)
        }
        return null
    }

    fun update(storeUpdateRequest: StoreUpdateRequest):StoreUpdateResponse?{
        val store = storeRepo.findById(storeUpdateRequest.id).orElse(null)
        if(store != null){
            val updatedStore = StoreDataValidation().validateUpdate(store, storeUpdateRequest)
            storeRepo.save(updatedStore)
            return StoreUpdateResponse(updatedStore.id,
                updatedStore.unitPrice,
                updatedStore.floor,
                updatedStore.rented)
        }
        return null
    }

    fun updateType(storeId:Long,typeId:Long):StoreResponse?{
        val type = storeTypeService.getById(typeId)
        val store = getById(storeId)
        if(type != null && store != null){
            store.storeType = type
            storeRepo.save(store)
            type.stores?.remove(store)
            storeTypeService.save(type)
            return StoreResponse(store.id,store.unitPrice,store.floor, store.storeType.id,store.img?.imgUrl)
        }
        return null
    }
}