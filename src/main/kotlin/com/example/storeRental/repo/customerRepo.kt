package com.example.storeRental.repo

import com.example.storeRental.domain.CustomerModel
import com.example.storeRental.domain.StoreModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager

@Repository
interface CustomerRepo:JpaRepository<CustomerModel, Long>, JpaSpecificationExecutor<CustomerModel> {

    fun findByName(name:String):Optional<CustomerModel>

    @Query("Select store_id from rental_detail" +
          " inner join (SELECT rental.id as rentalId FROM rental INNER JOIN customer ON rental.cus_id = customer.id WHERE customer.id = :cusId) as cusRent" +
          " on rental_detail.rental_id = cusRent.rentalId",
          nativeQuery = true
    )
    fun getRentedStoreIDs(cusId: Long):List<Long>

    @Query("Select rentStore.rental_id From customer Inner Join (select * from rental inner join rental_detail On rental.id = rental_detail.rental_id " +
          "Where rental_detail.store_id = :storeId) as rentStore on customer.id = rentStore.cus_Id " +
          "Where customer.id = :cusId",
      nativeQuery = true)
    fun getRentalIdByStoreId(cusId:Long, storeId:Long):Long
}