package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "storetype")
class StoreTypeModel(
    var typeName:String,
    @JsonIgnore
    @OneToMany(mappedBy = "storeType")
    var stores:MutableList<StoreModel>? = null
):BaseModel() {
}