package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class Role (
    @Size(max = 16)
    @Column(length = 16)
    var name:String,
    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    var user:MutableSet<User>?=null
    ): BaseModel()