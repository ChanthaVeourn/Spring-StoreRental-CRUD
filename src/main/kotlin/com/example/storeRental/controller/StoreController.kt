package com.example.storeRental.controller

import com.example.storeRental.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/store")
class StoreController(private val storeService: StoreService) {
@PostMapping
fun addNewImage(@RequestParam storeId:Long, @RequestParam imgUrl:String):ResponseEntity<String>{
    storeService.setImage(storeId, imgUrl)
    return ResponseEntity.status(201).build()
}
}