package com.example.storeRental.repo

import com.example.storeRental.dto.CustomerDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepo:JpaRepository<com.example.storeRental.domain.Customer, Long>, JpaSpecificationExecutor<com.example.storeRental.domain.Customer> {

    fun findByName(name:String): Optional<List<CustomerDto>>

    @Query("select store_id from customer c " +
            "join rental r on c.id = r.cus_id " +
            "join rental_detail rd on r.id = rd.rental_id " +
            "join store s on rd.store_id = s.id " +
            "where c.id = :cusId",
            nativeQuery = true
    )
      fun getRentedStoreIDs(cusId: Long):List<Long>

    @Query("select rental_id from customer c "+
            "join rental r on c.id = r.cus_id" +
            "join rental_detail rd on r.id = rd.rental_id" +
            "join store s on s.id = rd.store_id" +
            "where store.id = :storeId and customer.id = :cusId",
            nativeQuery = true)
    fun getRentalIdByStoreId(cusId:Long, storeId:Long):Long

    fun findByPhoneStartingWith(prefix:String):List<CustomerDto>?

}