package com.example.storeRental.repo

import com.example.storeRental.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepo:JpaRepository<Role,Long>