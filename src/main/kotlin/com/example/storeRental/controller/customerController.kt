package com.example.storeRental.controller

import com.example.storeRental.domain.Customer
import com.example.storeRental.domain.Rental
import com.example.storeRental.service.CustomerService
import com.example.storeRental.utils.dto.CustomerDto
import com.example.storeRental.utils.dto.RentalDetailDto
import com.example.storeRental.utils.dto.CustomerNameIdDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/customer")
class CustomerController(private val customerService: CustomerService){

    @GetMapping
    fun getAllCustomer(): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok().body(customerService.getAll())
    }

    @GetMapping("/{name}")
    fun getByName(@PathVariable name:String):List<CustomerDto>{
        return customerService.getByName(name)
    }

    @GetMapping("/phone")
    fun getByPhonePrefix(@RequestParam preNum:String):ResponseEntity<List<CustomerDto>>{
        val res = customerService.findByPhonePrefix(preNum)
        if(res != null)
            return ResponseEntity.ok(res)
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/rents")
    fun getAllRentals(@RequestParam(value = "cusId", required = true) cusId:Long): ResponseEntity<List<Rental>> {
        return ResponseEntity.ok().body(customerService.getAllRental(cusId))
    }

    @PostMapping("/rent-details")
    fun getAllRentDetails(@RequestParam(required = true) rental_id:Long):ResponseEntity<List<RentalDetailDto>>{
        return ResponseEntity.ok().body(customerService.getAllRentalDetailsByRentalId(rental_id))
    }

    @PostMapping("/rent-store")
    fun rentStore(@RequestParam("cusId", required = true) cusId:Long,
                  @RequestParam("storeId", required = true) storeId:Long):ResponseEntity<String>{
        if(customerService.rentStore(cusId,storeId))
            return ResponseEntity.accepted().build()
        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/remove-rent")
    fun removeRental(@RequestParam cusId:Long, @RequestParam storeId:Long):ResponseEntity<String>{
        if(customerService.removeRentStoreByStoreId(cusId, storeId)){
            return ResponseEntity.accepted().build()
        }
        return ResponseEntity.notFound().build()
    }

}