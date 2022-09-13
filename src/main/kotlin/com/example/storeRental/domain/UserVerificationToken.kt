package com.example.storeRental.domain

import javax.persistence.*

@Entity
class UserVerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    var token:String,
    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "user_token_fk"), nullable = false)
    var user:User
)