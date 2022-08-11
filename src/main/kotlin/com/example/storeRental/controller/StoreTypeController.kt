package com.example.storeRental.controller

import com.example.storeRental.domain.StoreTypeModel
import com.example.storeRental.service.StoreTypeService
import com.example.storeRental.utils.requestClass.StoreTypeUpdateRequest
import com.example.storeRental.utils.responseClass.StoreTypeResponse
import org.springframework.boot.autoconfigure.session.StoreType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/store-type")
class StoreTypeController(private val storeTypeService: StoreTypeService) {

    @GetMapping
    fun getAll():List<StoreTypeModel>{
        return storeTypeService.getAll()
    }

    @PutMapping("/update")
    fun updateStoreName(@RequestBody storeTypeUpdateRequest: StoreTypeUpdateRequest): StoreTypeResponse {
        val updatedStoreType = storeTypeService.update(storeTypeUpdateRequest)
        return StoreTypeResponse(
            updatedStoreType?.id,
            updatedStoreType?.typeName,
            updatedStoreType?.createdDate
        )
    }

    @PostMapping
    fun addStoreType(@RequestParam typeName:String):ResponseEntity<String>{
        val st = StoreTypeModel(typeName)
        storeTypeService.save(st)
       return ResponseEntity.status(201).body("CREATED")
    }

}