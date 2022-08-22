package com.example.storeRental.service

import com.example.storeRental.domain.StoreType
import com.example.storeRental.repo.StoreTypeRepo
import com.example.storeRental.utils.requestClass.StoreTypeUpdateRequest
import com.example.storeRental.dto.StoreTypeDto
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class StoreTypeService(
    private val storeTypeRepo: StoreTypeRepo
    ):BaseSevice<StoreType, Long>{

    override fun save(model: StoreType) {
        storeTypeRepo.save(model)
    }

    override fun getById(id: Long): StoreType? {
        return storeTypeRepo.findById(id).orElse(null)
    }

    fun getAll():List<StoreType>{
        return storeTypeRepo.findAll()
    }

    override fun deleteById(id: Long) {
        storeTypeRepo.deleteById(id)
    }

   fun update(storeUReq: StoreTypeUpdateRequest): StoreTypeDto? {
        val storeType = storeTypeRepo.findById(storeUReq.id).orElse(null)
        if(storeType != null){
            storeType.typeName = storeUReq.typeName
            storeType.createdDate = LocalDate.parse(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
            storeTypeRepo.save(storeType)
            return StoreTypeDto(
                storeType.id,
                storeType.typeName,
                storeType.createdDate
            )
        }
       return null
    }

    fun createStoreType(typeName:String){
        val st = StoreType(typeName)
        storeTypeRepo.save(st)
    }
}