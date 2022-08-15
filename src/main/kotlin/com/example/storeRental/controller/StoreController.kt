package com.example.storeRental.controller

import com.example.storeRental.domain.StoreModel
import com.example.storeRental.service.StoreService
import com.example.storeRental.service.StoreTypeService
import com.example.storeRental.utils.requestClass.StoreCreateRequest
import com.example.storeRental.utils.requestClass.StoreUpdateRequest
import com.example.storeRental.utils.responseClass.StoreResponse
import com.example.storeRental.utils.responseClass.StoreUpdateImgResponse
import com.example.storeRental.utils.responseClass.StoreUpdateResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/store")
class StoreController(private val storeService: StoreService) {

    @GetMapping
    fun getAllStore():List<StoreModel>{
        return storeService.getAll()
    }

    @GetMapping("/rented")
    fun getAllRented():List<StoreModel>{
        return storeService.getAllRented()
    }

    @PostMapping
    fun setNewImage(@RequestParam storeId:Long, @RequestParam imgUrl:String):ResponseEntity<String>{
        storeService.setImage(storeId, imgUrl)
        return ResponseEntity.status(201).build()
    }

    @PostMapping("/create")
    fun addNewStore(@RequestBody storeCreateRequest: StoreCreateRequest):ResponseEntity<StoreResponse>{
        val res = storeService.createStore(storeCreateRequest)
        if (res != null){
            return ResponseEntity.status(201).body(res)
        }
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/update")
    fun updateStore(@RequestBody storeUpdateRequest: StoreUpdateRequest):ResponseEntity<StoreUpdateResponse>{
        if(storeService.update(storeUpdateRequest) != null )
            return ResponseEntity.accepted().body(storeService.update(storeUpdateRequest))
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/update-type")
    fun updateType(@RequestParam storeId:Long, @RequestParam typeId:Long):ResponseEntity<StoreResponse>{
       val response = storeService.updateType(storeId, typeId)
        if(response != null)
            return ResponseEntity.accepted().body(storeService.updateType(storeId, typeId)!!)
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/update-image")
    fun updateImage(@RequestParam storeId:Long, @RequestParam imgUrl:String):ResponseEntity<StoreUpdateImgResponse>{
        val response = storeService.updateImage(storeId, imgUrl)
        if(response != null)
            return ResponseEntity.accepted().body(response)
        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/delete")
    fun remove(@RequestParam storeId:Long){
        storeService.deleteById(storeId)
    }

}