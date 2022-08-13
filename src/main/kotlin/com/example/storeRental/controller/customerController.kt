package com.example.storeRental.controller

import com.example.storeRental.domain.CustomerModel
import com.example.storeRental.domain.RentalDetailModel
import com.example.storeRental.domain.RentalModel
import com.example.storeRental.service.CustomerService
import com.example.storeRental.utils.responseClass.CusNameIdRespose
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/customer")
class CustomerController(private val customerService: CustomerService){

    @GetMapping
    fun getAllCustomer(): ResponseEntity<List<CustomerModel>> {
        return ResponseEntity.ok().body(customerService.getAll())
    }

    @GetMapping("/{name}")
    fun getByName(@PathVariable name:String):List<CusNameIdRespose>{
        return customerService.getByName(name)
    }

    @PostMapping("/rents")
    fun getAllRentals(@RequestParam(value = "cusId", required = true) cusId:Long): ResponseEntity<List<RentalModel>> {
        return ResponseEntity.ok().body(customerService.getAllRental(cusId))
    }

    @PostMapping("/rent-details")
    fun getAllRentDetails(@RequestParam(required = true) rental_id:Long):ResponseEntity<List<RentalDetailModel>>{
        return ResponseEntity.ok().body(customerService.getAllRentalDetails(rental_id))
    }

    @PostMapping("/rent-store")
    fun rentStore(@RequestParam("cusId", required = true) cusId:Long,
                  @RequestParam("storeId", required = true) storeId:Long):ResponseEntity<String>{
        if(customerService.rentStore(cusId,storeId))
            return ResponseEntity.accepted().build()
        return ResponseEntity.badRequest().build()
    }

    @PostMapping("/remove-rent")
    fun removeRental(@RequestParam cusId:Long, @RequestParam storeId:Long):ResponseEntity<String>{
        if(customerService.removeRentStoreByStoreId(cusId, storeId)){
            return ResponseEntity.accepted().build()
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/remove-rent")
    fun removeRental():ResponseEntity<String>{
        if(customerService.removeRentStoreByStoreId(3, 8)){
            return ResponseEntity.accepted().body("removed")
        }
        return ResponseEntity.notFound().build()
    }

}