package com.example.storeRental.utils.requestClass

import org.springframework.lang.NonNull

data class RentalExchangeStoreRequest(
    @NonNull
    var wanted_rental_id:Long,
    @NonNull
    var cus_id:Long,
    @NonNull
    var cus_rental_id:Long
)