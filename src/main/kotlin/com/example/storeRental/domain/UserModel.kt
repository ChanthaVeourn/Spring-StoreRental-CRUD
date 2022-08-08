package com.example.storeRental.domain

import org.springframework.security.crypto.bcrypt.BCrypt
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "usertable")
class UserModel(
    @Column(name = "username")
    var username: String,
    @Column(name = "email")
    private var email: String
): BaseModel(){
    fun setPassword(rawPassword: String) {
        hashPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }
    private lateinit var hashPassword: String
    fun getHashPwd() = hashPassword
}
