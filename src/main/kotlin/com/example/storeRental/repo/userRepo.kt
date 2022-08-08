package com.example.storeRental.repo

import com.example.storeRental.domain.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo:JpaRepository<UserModel, Long> {
    fun findById(id: Long?):Optional<UserModel>
    fun findByEmail(email:String):Optional<UserModel>
}