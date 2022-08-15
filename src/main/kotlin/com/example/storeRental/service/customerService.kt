package com.example.storeRental.service

import com.example.storeRental.domain.CustomerModel
import com.example.storeRental.domain.RentalDetailModel
import com.example.storeRental.domain.RentalModel
import com.example.storeRental.domain.StoreModel
import com.example.storeRental.repo.CustomerRepo
import com.example.storeRental.repo.RentalDetailRepo
import com.example.storeRental.repo.RentalRepo
import com.example.storeRental.repo.StoreRepo
import com.example.storeRental.utils.responseClass.CusNameIdRespose
import org.springframework.stereotype.Service


@Service
class CustomerService(
                        private val customerRepo: CustomerRepo,
                        private val storeRepo: StoreRepo,
                        private val rentalRepo: RentalRepo,
                        private val rentalDetailRepo: RentalDetailRepo
                        ):BaseSevice<com.example.storeRental.domain.CustomerModel, Long>
{
    //basic
    override fun save(model: com.example.storeRental.domain.CustomerModel) {
        customerRepo.save(model)
    }

    override fun getById(id: Long): CustomerModel {
        return customerRepo.findById(id).orElse(null)
    }

    override fun deleteById(id: Long) {
        customerRepo.deleteById(id)
    }
    fun getAll():List<com.example.storeRental.domain.CustomerModel>{
        return customerRepo.findAll()
    }

    fun getByName(name:String):List<CusNameIdRespose> {
        val cusNameId = customerRepo.findByName(name).orElse(null)
        var cusRes = mutableListOf<CusNameIdRespose>()
        cusNameId.stream().forEach{cus->run{
            cusRes.add(CusNameIdRespose(cus.name,cus.id))
        }}
        return cusRes.toList()
    }

    //customer action
    fun rentStore(cusId: Long,storeId: Long):Boolean{
        //check if customer have AAA (Authentication authorization Account)
        val customer: com.example.storeRental.domain.CustomerModel? = customerRepo.findById(cusId).orElse(null)
        val store = storeRepo.findById(storeId).orElse(null)
        if(customer != null && store != null) {
            if(!store.rented){
                store.rented = true
                val newRental = RentalModel(customer)
                rentalRepo.save(newRental)
                val newRentalDetail = RentalDetailModel(store,newRental)
                rentalDetailRepo.save(newRentalDetail)
                return true
            }
            return false
        }
        return false
    }

    fun getAllRental(cusId: Long): List<RentalModel>? {
        val customer = customerRepo.findById(cusId).orElse(null)
        return customer.rentals
    }

    fun getAllRentalDetails(rental_id:Long):List<RentalDetailModel>?{
        val rentD = rentalRepo.findById(rental_id).orElse(null)
            return rentD.rentalDetails
    }

    fun getAllRentedStore(cusId: Long): List<StoreModel>? {
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

    fun findByPhonePrefix(prefix:String):List<CusNameIdRespose>?{
        val cusNameId = customerRepo.findByPhoneStartingWith(prefix)
        if(cusNameId!!.isNotEmpty()) {
            val cusResponse = mutableListOf<CusNameIdRespose>()
            cusNameId.stream().forEach { cus -> run { cusResponse.add(CusNameIdRespose(cus.name, cus.id)) } }
            return cusResponse
        }
        return null
    }

}

