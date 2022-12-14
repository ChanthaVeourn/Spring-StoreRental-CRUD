package com.example.storeRental.service

import com.example.storeRental.domain.Rental
import com.example.storeRental.repo.RentalRepo
import com.example.storeRental.utils.requestClass.RentalExchangeStoreRequest
import com.example.storeRental.dto.RentalDto
import com.example.storeRental.utils.responseClass.ResponseData
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class RentalService(private val rentalRepo: RentalRepo,
                    private val customerService: CustomerService
                    ):BaseSevice<Rental, Long>
{
    override fun save(model: Rental) {
        rentalRepo.save(model)
    }

    fun saveAllRental(rentals: List<Rental>){
        rentalRepo.saveAll(rentals)
    }

    fun getRentalById(id: Long): RentalDto? {
        val rental = rentalRepo.findById(id).orElse(null)
        if(rental != null)
            return RentalDto(rental.id,rental.customer.id,rental.createdDate,rental.createdDate)
        return null
    }

    fun deleteRentalById(id: Long):Boolean{
    val rental = rentalRepo.findById(id).orElse(null)
      if(rental != null) {
          rentalRepo.delete(rental)
          return true
      }
      return false
    }

    fun getByCusId(cusId:Long):List<Long>?{
        return rentalRepo.getRentalCusId(cusId)
    }

    fun findAllRental():ResponseData?{
        val rentalList = rentalRepo.findAll()
        val rentalRes = mutableListOf<RentalDto>()
        rentalList.stream().forEach {
            rent ->
            run {
                rentalRes.add(RentalDto(rent.id, rent.customer.id, rent.createdDate, rent.updatedDate))
            }
        }
        return  ResponseData(statusCode = 200,data = rentalRes.toList())
    }

    //change customer (old-cus remove rental, new-cus get old-cus's rental)
    fun changeCustomer(rentalId:Long, cusId:Long): RentalDto?{

        val rental = getById(rentalId)
        val newCus = customerService.getById(cusId)

        if (rental != null && newCus != null) {
            val oldCus = rental.customer
            //rental update customer
            rental.customer = newCus
            rentalRepo.save(rental)

            newCus.rentals?.add(rental)
            oldCus.rentals?.remove(rental)

            customerService.save(newCus)
            customerService.save(oldCus)

            return RentalDto(
                id = rental.id,
                cus_id = rental.customer.id,
                updated = rental.updatedDate,
                created = rental.createdDate
            )
        }
        return null
    }

    // in case two customer exchange store
     fun exchangeRental(rentalExchangeStoreRequest: RentalExchangeStoreRequest):List<RentalDto>? {

        val wantedRental = getById(rentalExchangeStoreRequest.wanted_rental_id)

        if(wantedRental != null) {

             val newCus = customerService.getById(rentalExchangeStoreRequest.cus_id)
             val newCusRental= getById(rentalExchangeStoreRequest.cus_rental_id)
             val oldCus = wantedRental.customer
             val rentalResList = mutableListOf<RentalDto>()
             //add wanted-rental to new-cus
             wantedRental.customer = newCus!!
             wantedRental.updatedDate = LocalDate.parse(
                 LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                 DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))

             //add new-customer's rental to old-cus
             newCusRental?.customer = oldCus
             newCusRental?.updatedDate = LocalDate.parse(
                 LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                 DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))

             rentalRepo.saveAll(listOf(wantedRental, newCusRental))

             //remove rental from new-cus
            newCus.rentals.remove(newCusRental)

             //remove rental from old-cus
            oldCus.rentals.remove(wantedRental)

             customerService.save(oldCus)
             customerService.save(newCus)

             val exchangedRentalsList = listOf(wantedRental, newCusRental)
             exchangedRentalsList.stream().forEach { rental->
                 run {
                     rentalResList.add(
                         RentalDto(
                             rental?.id, rental?.customer?.id, rental?.createdDate, rental?.updatedDate
                         )
                     )
                 }
             }

             return rentalResList.toList()
        }
        return null
    }

    override fun deleteById(id: Long) {
        rentalRepo.deleteById(id)
    }

    override fun getById(id: Long): Rental? {
        return rentalRepo.findById(id).orElse(null)
    }
}