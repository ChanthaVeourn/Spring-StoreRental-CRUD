package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.validation.constraints.Size

@Entity
class StoreType(
    @Size(max = 32)
    @Column(length = 32)
    var typeName:String,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storeType")
    var stores:MutableList<Store>? = null

):BaseModel()