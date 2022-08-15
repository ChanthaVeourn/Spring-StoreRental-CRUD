package com.example.storeRental.controller

import com.example.storeRental.service.RentalService
import com.example.storeRental.utils.requestClass.RentalExchangeStoreRequest
import com.example.storeRental.utils.responseClass.RentalResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("api/rental")
class RentalController(private val rentalService: RentalService){

    @GetMapping
    fun getAllRental():ResponseEntity<List<RentalResponse>>{
        return ResponseEntity.ok(rentalService.findAllRental())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id:Long):ResponseEntity<RentalResponse>{
        val rental = rentalService.getRentalById(id)
        if(rental != null)
            return ResponseEntity.ok(rental)
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/delete")
    fun deleteRental(@RequestParam rentalId:Long):ResponseEntity<String>{
        if(rentalService.deleteRentalById(rentalId)){
            return ResponseEntity.ok("deleted")
        }
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/exchange")
    fun exchangeStore(@RequestBody rentalExchangeStoreRequest: RentalExchangeStoreRequest):ResponseEntity<List<RentalResponse>>{
        val response = rentalService.exchangeRental(rentalExchangeStoreRequest)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/change-customer")
    fun changeCustomer(@RequestParam rentalId:Long, @RequestParam cusId:Long):ResponseEntity<RentalResponse>{
        val response = rentalService.changeCustomer(rentalId, cusId)!!
        return ResponseEntity.ok(response)
    }

}