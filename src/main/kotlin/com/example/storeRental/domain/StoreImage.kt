package com.example.storeRental.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.*


@Entity
class StoreImage(

    @NotNull
    @Column(length = 128)
    var imgUrl: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_store_id"))
    var store:Store? = null

):BaseModel()