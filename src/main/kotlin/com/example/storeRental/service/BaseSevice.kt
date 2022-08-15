package com.example.storeRental.service

interface BaseSevice<R, T> {
    fun save(model:R)
    fun getById(id:T):R?
    fun deleteById(id:T)
}