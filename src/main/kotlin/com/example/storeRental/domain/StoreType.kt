package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
class StoreType(
    var typeName:String,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REFRESH], mappedBy = "storeType")
    var stores:MutableList<Store>? = null

):BaseModel()