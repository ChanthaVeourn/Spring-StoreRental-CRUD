package com.example.storeRental.controller

import com.example.storeRental.domain.RentalModel
import com.example.storeRental.service.RentalService
import com.example.storeRental.utils.requestClass.RentalExchangeStoreRequest
import com.example.storeRental.utils.responseClass.RentalResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("api/rental")
class RentalController(private val rentalService: RentalService){

    @GetMapping
    fun getAllRental():ResponseEntity<List<RentalModel>>?{
        return ResponseEntity.ok(rentalService.findAllRental())
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
        val exchangedRentalsList = rentalService.exchangeRental(rentalExchangeStoreRequest)
        val response = mutableListOf<RentalResponse>()
        exchangedRentalsList!!.stream().forEach { rental->
            run {
                response.add(
                    RentalResponse(
                        rental.id, rental.customer.id, rental.createdDate, rental.updatedDate
                    )
                )
            }
        }
        return ResponseEntity.ok(response)
    }

    @PutMapping("/change-customer")
    fun changeCustomer(@RequestParam rentalId:Long, @RequestParam cusId:Long):ResponseEntity<RentalResponse>{
        val rentalChangeCus = rentalService.changeCustomer(rentalId, cusId)!!
        val response = RentalResponse(id = rentalChangeCus.id,
                                        cus_id = rentalChangeCus.customer.id,
                                        updated = rentalChangeCus.updatedDate,
                                        created = rentalChangeCus.createdDate)
        return ResponseEntity.ok(response)
    }
}