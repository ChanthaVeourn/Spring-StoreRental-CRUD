package com.example.storeRental.service

import com.example.storeRental.domain.StoreTypeModel
import com.example.storeRental.repo.StoreTypeRepo
import com.example.storeRental.utils.requestClass.StoreTypeUpdateRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class StoreTypeService(
    private val storeTypeRepo: StoreTypeRepo
    ):BaseSevice<StoreTypeModel, Long>{

    override fun save(model: StoreTypeModel) {
        storeTypeRepo.save(model)
    }

    override fun getById(id: Long): StoreTypeModel? {
        return storeTypeRepo.findById(id).orElse(null)
    }

    fun getAll():List<StoreTypeModel>{
        return storeTypeRepo.findAll()
    }

    override fun deleteById(id: Long) {
        storeTypeRepo.deleteById(id)
    }

   fun update(storeUReq: StoreTypeUpdateRequest):StoreTypeModel? {
        val storeType = storeTypeRepo.findById(storeUReq.id!!).orElse(null)
        if(storeType != null){
            storeType.typeName = storeUReq.typeName
            storeType.createdDate = LocalDate.parse(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
            storeTypeRepo.save(storeType)
            return storeType
        }
       return null
    }

}