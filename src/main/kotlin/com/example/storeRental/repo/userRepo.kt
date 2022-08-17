package com.example.storeRental.repo

import com.example.storeRental.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo:JpaRepository<User, Long> {
    fun findById(id: Long?):Optional<User>
    fun findByEmail(email:String):Optional<User>
}