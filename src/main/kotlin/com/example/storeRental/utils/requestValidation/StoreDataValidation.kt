package com.example.storeRental.utils.requestValidation

import com.example.storeRental.domain.Store
import com.example.storeRental.utils.requestClass.StoreUpdateRequest

class StoreDataValidation {

    fun validateUpdate(store:Store, storeUpdateRequest:StoreUpdateRequest):Store{
        if(storeUpdateRequest.unitPrice != 0.0 &&
            storeUpdateRequest.unitPrice != null)
            store.unitPrice = storeUpdateRequest.unitPrice
        if(storeUpdateRequest.floor != "" &&
            storeUpdateRequest.floor != null)
            store.floor = storeUpdateRequest.floor
        if(storeUpdateRequest.rented != null)
            store.rented = storeUpdateRequest.rented
        return store
    }

}