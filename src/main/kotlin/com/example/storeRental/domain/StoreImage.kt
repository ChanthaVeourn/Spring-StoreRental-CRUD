package com.example.storeRental.domain

import javax.persistence.*


@Entity
class StoreImage(

    @Column(nullable = false, length = 128)
    var imgUrl: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_store_id"))
    var store:Store? = null

):BaseModel()