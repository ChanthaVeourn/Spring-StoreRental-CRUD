package com.example.storeRental.repo

import com.example.storeRental.domain.UserVerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserTokenRepo:JpaRepository<UserVerificationToken, Long> {
}