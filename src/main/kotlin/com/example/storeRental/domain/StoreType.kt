package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class StoreType(
    var typeName:String,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storeType")
    var stores:MutableList<Store>? = null

):BaseModel()