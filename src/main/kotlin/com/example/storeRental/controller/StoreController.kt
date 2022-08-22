package com.example.storeRental.controller

import com.example.storeRental.domain.Store
import com.example.storeRental.service.StoreService
import com.example.storeRental.utils.requestClass.StoreCreateRequest
import com.example.storeRental.utils.requestClass.StoreUpdateRequest
import com.example.storeRental.dto.StoreDto
import com.example.storeRental.dto.StoreUpdateImgDto
import com.example.storeRental.dto.StoreUpdateDto
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
    fun getAllStore():List<StoreDto>{
        return storeService.getAll()
    }

    @GetMapping("/rented")
    fun getAllRented():List<Store>{
        return storeService.getAllRented()
    }

    @GetMapping("/with-img")
    fun getStoresWithImage():List<StoreDto>{
        return storeService.getStoreWithImage()
    }

    @GetMapping("/no-img")
    fun getStoresNoImage():List<StoreDto>{
        return storeService.getStoreNoImage()
    }

    @PostMapping("/set-image")
    fun setNewImage(@RequestParam storeId:Long, @RequestParam imgUrl:String):ResponseEntity<String>{
        storeService.setImage(storeId, imgUrl)
        return ResponseEntity.status(201).build()
    }

    @PostMapping("/create")
    fun addNewStore(@RequestBody storeCreateRequest: StoreCreateRequest):ResponseEntity<StoreDto>{
        val res = storeService.createStore(storeCreateRequest)
        res?: return ResponseEntity.badRequest().build()
        return ResponseEntity.status(201).body(res)
    }

    @PutMapping("/update")
    fun updateStore(@RequestBody storeUpdateRequest: StoreUpdateRequest):ResponseEntity<StoreUpdateDto>{
        val res = storeService.update(storeUpdateRequest)
        res?: return ResponseEntity.badRequest().build()
        return ResponseEntity.accepted().body(res)
    }

    @PutMapping("/update-type")
    fun updateType(@RequestParam storeId:Long, @RequestParam typeId:Long):ResponseEntity<StoreDto>{
       val response = storeService.updateType(storeId, typeId)
        if(response != null)
            return ResponseEntity.accepted().body(storeService.updateType(storeId, typeId)!!)
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/update-image")
    fun updateImage(@RequestParam storeId:Long, @RequestParam imgUrl:String):ResponseEntity<StoreUpdateImgDto>{
        val store = storeService.getById(storeId)

        val response = storeService.updateImage(store!!, imgUrl)
        if(response != null)
            return ResponseEntity.accepted().body(response)
        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/delete")
    fun remove(@RequestParam storeId:Long){
        storeService.deleteById(storeId)
    }

}