package com.example.storeRental.controller

import com.example.storeRental.domain.StoreType
import com.example.storeRental.service.StoreTypeService
import com.example.storeRental.utils.requestClass.StoreTypeUpdateRequest
import com.example.storeRental.utils.dto.StoreTypeDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/store-type")
class StoreTypeController(private val storeTypeService: StoreTypeService) {

    @GetMapping
    fun getAll():List<StoreType>{
        return storeTypeService.getAll()
    }
    @GetMapping("/{id}")
    fun getById(@PathVariable id:Long):ResponseEntity<StoreType>{
        val res = storeTypeService.getById(id)
        if(res != null)
            return ResponseEntity.ok(res)
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/update")
    fun updateStoreName(@RequestBody storeTypeUpdateRequest: StoreTypeUpdateRequest):ResponseEntity<StoreTypeDto> {
        val updatedStoreType = storeTypeService.update(storeTypeUpdateRequest)
        if(updatedStoreType != null)
            return ResponseEntity.accepted().body(updatedStoreType)
        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun addStoreType(@RequestParam typeName:String):ResponseEntity<String>{
        storeTypeService.createStoreType(typeName)
       return ResponseEntity.status(201).body("CREATED")
    }

}