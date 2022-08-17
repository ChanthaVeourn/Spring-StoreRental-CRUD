package com.example.storeRental.service

import com.example.storeRental.utils.requestValidation.StoreDataValidation
import com.example.storeRental.domain.StoreImage
import com.example.storeRental.domain.Store
import com.example.storeRental.repo.StoreImageRepo
import com.example.storeRental.repo.StoreRepo
import com.example.storeRental.utils.requestClass.StoreCreateRequest
import com.example.storeRental.utils.requestClass.StoreUpdateRequest
import com.example.storeRental.utils.dto.StoreDto
import com.example.storeRental.utils.dto.StoreUpdateImgDto
import com.example.storeRental.utils.dto.StoreUpdateDto
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class StoreService(private val storeImageRepo: StoreImageRepo,
                   private val storeRepo: StoreRepo,
                    private val storeTypeService: StoreTypeService):BaseSevice<Store, Long>{

    override fun save(model: Store) {
        storeRepo.save(model)
    }

    override fun getById(id: Long): Store? {
        return storeRepo.findById(id).orElse(null)
    }

    fun getAll():List<Store>{
        return storeRepo.findAll()
    }

    fun getAllRented():List<Store>{
        return storeRepo.findByRentedTrue()
    }

    override fun deleteById(id: Long) {
        storeRepo.deleteById(id)
    }

    fun createStore(storeCreateRequest: StoreCreateRequest): StoreDto?{

        val storeType = storeTypeService.getById(storeCreateRequest.typeId)

        if(storeType != null){
            val newStore = Store(storeType = storeType,
                unitPrice = storeCreateRequest.unitPrice,
                floor = storeCreateRequest.floor)
            storeRepo.save(newStore)
            if(storeCreateRequest.imgUrl != null && storeCreateRequest.imgUrl != ""){
                val newImg = StoreImage(imgUrl = storeCreateRequest.imgUrl, store = newStore)
                storeImageRepo.save(newImg)

            }
            return StoreDto(newStore.id,
                newStore.unitPrice,
                newStore.floor,
                storeCreateRequest.typeId,
                storeCreateRequest.imgUrl)
        }
        return null
    }

    fun setImage(storeId:Long, imgUrl:String){
        val store = storeRepo.findById(storeId).orElse(null)
        val newImg = StoreImage(imgUrl,store)
        storeImageRepo.save(newImg)
    }

    fun updateImage(storeId:Long, imgUrl:String): StoreUpdateImgDto?{
        val store = getById(storeId)
        val oldImg = store?.img

        if(store != null){
            store.img = null
            store.updatedDate = LocalDate.parse(
                LocalDate.now().toString(),
                DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
            storeImageRepo.delete(oldImg!!)
            val newImg = StoreImage(imgUrl, store)
            storeImageRepo.save(newImg)
            return StoreUpdateImgDto(storeId, imgUrl, store.updatedDate)
        }
        return null
    }

    fun update(storeUpdateRequest: StoreUpdateRequest): StoreUpdateDto?{
        val store = storeRepo.findById(storeUpdateRequest.id).orElse(null)

        if(store != null){
            val updatedStore = StoreDataValidation().validateUpdate(store, storeUpdateRequest)
            storeRepo.save(updatedStore)
            return StoreUpdateDto(updatedStore.id,
                updatedStore.unitPrice,
                updatedStore.floor,
                updatedStore.rented)
        }
        return null
    }

    fun updateType(storeId:Long,typeId:Long): StoreDto?{
        val type = storeTypeService.getById(typeId)
        val store = getById(storeId)
        if(type != null && store != null){
            store.storeType = type
            storeRepo.save(store)
            type.stores?.remove(store)
            storeTypeService.save(type)
            return StoreDto(store.id,store.unitPrice,store.floor, store.storeType.id,store.img?.imgUrl)
        }
        return null
    }

}