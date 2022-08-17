package com.example.storeRental.repo

import com.example.storeRental.utils.dto.CustomerDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepo:JpaRepository<com.example.storeRental.domain.Customer, Long>, JpaSpecificationExecutor<com.example.storeRental.domain.Customer> {

    fun findByName(name:String): Optional<List<CustomerDto>>

    @Query("Select store_id from rental_detail" +
            " inner join (SELECT rental.id as rentalId FROM rental INNER JOIN customer ON rental.cus_id = customer.id " +
            "WHERE customer.id = :cusId) as cusRent" +
            " on rental_detail.rental_id = cusRent.rentalId",
          nativeQuery = true
    )
    fun getRentedStoreIDs(cusId: Long):List<Long>

    @Query("Select rentStore.rental_id From customer Inner Join (select * from rental inner join rental_detail On rental.id = rental_detail.rental_id " +
          "Where rental_detail.store_id = :storeId) as rentStore on customer.id = rentStore.cus_Id " +
          "Where customer.id = :cusId",
      nativeQuery = true)
    fun getRentalIdByStoreId(cusId:Long, storeId:Long):Long

    fun findByPhoneStartingWith(prefix:String):List<CustomerDto>?

}