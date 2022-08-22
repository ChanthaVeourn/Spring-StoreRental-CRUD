package com.example.storeRental.service

import com.example.storeRental.domain.Customer
import com.example.storeRental.domain.RentalDetail
import com.example.storeRental.domain.Rental
import com.example.storeRental.domain.Store
import com.example.storeRental.repo.CustomerRepo
import com.example.storeRental.repo.RentalDetailRepo
import com.example.storeRental.repo.RentalRepo
import com.example.storeRental.repo.StoreRepo
import com.example.storeRental.dto.CustomerDto
import com.example.storeRental.dto.RentalDetailDto
import org.springframework.stereotype.Service


@Service
class CustomerService(
                        private val customerRepo: CustomerRepo,
                        private val storeRepo: StoreRepo,
                        private val rentalRepo: RentalRepo,
                        private val rentalDetailRepo: RentalDetailRepo
                        ):BaseSevice<Customer, Long>
{
    //basic
    override fun save(model: Customer) {
        customerRepo.save(model)
    }

    override fun getById(id: Long): Customer? {
        return customerRepo.findById(id).orElse(null)
    }

    override fun deleteById(id: Long) {
        customerRepo.deleteById(id)
    }
    fun getAll():List<Customer>{
        return customerRepo.findAll()
    }

    fun getByName(name:String):List<CustomerDto> {
        val cusNameId = customerRepo.findByName(name).orElse(null)
        val cusRes = mutableListOf<CustomerDto>()
        cusNameId.stream().forEach{cus->run{
            cusRes.add(CustomerDto(cus.id, cus.name, cus.address, cus.phone))
        }}
        return cusRes.toList()
    }

    //customer action
    fun rentStore(cusId: Long,storeId: Long):Boolean{

        val customer: Customer? = customerRepo.findById(cusId).orElse(null)
        val store = storeRepo.findById(storeId).orElse(null)

        if(customer != null && store != null) {
            if(!store.rented){
                store.rented = true
                val newRental = Rental(customer)
                rentalRepo.save(newRental)
                val newRentalDetail = RentalDetail(store,newRental)
                rentalDetailRepo.save(newRentalDetail)
                return true
            }
            return false
        }
        return false
    }

    fun getAllRental(cusId: Long): List<Rental>? {
        val customer = customerRepo.findById(cusId).orElse(null)
        return customer.rentals
    }

    fun getAllRentalDetailsByRentalId(rental_id:Long):List<RentalDetailDto>?{
        return rentalDetailRepo.findByRentalId(rental_id)
    }

    fun getAllRentedStore(cusId: Long): List<Store>? {
      val storeIds = customerRepo.getRentedStoreIDs(cusId)
        return storeRepo.findAllById(storeIds)
    }

    fun removeRentStoreByStoreId(customerId:Long, storeId:Long):Boolean{

        val customer = customerRepo.findById(customerId).orElse(null)
        val store = storeRepo.findById(storeId).orElse(null)

        if(customer != null && store != null ){
            val rentalId = customerRepo.getRentalIdByStoreId(customerId, storeId)
            val rental = customer.rentals.first { it.id == rentalId }
            customer.rentals.remove(rental)
            customerRepo.save(customer)
            store.rented = false
            storeRepo.save(store)
            return true
        }
        return false
    }

    fun findByPhonePrefix(prefix:String):List<CustomerDto>?{
        val cusNameId = customerRepo.findByPhoneStartingWith(prefix)
        if(cusNameId!!.isNotEmpty()) {
            val cusResponse = mutableListOf<CustomerDto>()
            cusNameId.stream().forEach { cus -> run { cusResponse.add(CustomerDto(cus.id, cus.name, cus.address, cus.phone)) } }
            return cusResponse
        }
        return null
    }

}

