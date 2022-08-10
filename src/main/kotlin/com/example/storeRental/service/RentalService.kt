package com.example.storeRental.service

import com.example.storeRental.domain.RentalModel
import com.example.storeRental.repo.RentalRepo
import com.example.storeRental.utils.RequestClass.RentalExchangeStoreRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class RentalService(private val rentalRepo: RentalRepo,
                    private val customerService: CustomerService
                    ):BaseSevice<RentalModel, Long>
{
    override fun save(model: RentalModel) {
        rentalRepo.save(model)
    }

    fun saveAllRental(rentals: List<RentalModel>){
        rentalRepo.saveAll(rentals)
    }

    override fun getById(id: Long): RentalModel {
        return rentalRepo.findById(id).orElse(null)
    }

    fun deleteRentalById(id: Long):Boolean{
    val rental = rentalRepo.findById(id).orElse(null)
      if(rental != null) {
          rentalRepo.delete(rental)
          return true
      }
      return false
    }

    fun findAllRental():List<RentalModel>?{
        return rentalRepo.findAll()
    }

    override fun deleteById(id: Long) {

    }

    // in case two customer exchange store
     fun exchangeRental(rentalExchangeStoreRequest: RentalExchangeStoreRequest):List<RentalModel>? {
        val wantedRental = getById(rentalExchangeStoreRequest.wanted_rental_id)?:null

         if(wantedRental != null) {

             val newCus = customerService.getById(rentalExchangeStoreRequest.cus_id)
             val newCusRental= getById(rentalExchangeStoreRequest.cus_rental_id)
             val oldCus = wantedRental.customer

             //add wanted-rental to new-cus
             wantedRental.customer = newCus
             wantedRental.updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm"))

             ////add new-customer's rental to old-cus
             newCusRental.customer = oldCus
             newCusRental.updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm"))

             rentalRepo.saveAll(listOf(wantedRental, newCusRental))

             //remove rental from new-cus
             newCus.rentals.remove(newCusRental)

             //remove rental from old-cus
             oldCus.rentals.remove(wantedRental)

             customerService.save(oldCus)
             customerService.save(newCus)

             return listOf(wantedRental, newCusRental)
         }
        return null
    }

    override fun update(model: RentalModel) {
    }

    override fun delete(model: RentalModel) {
    }
}