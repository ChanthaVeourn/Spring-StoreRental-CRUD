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
    private lateinit var hashPassword: String
    fun setHashPwd(hashedPws:String){
        hashPassword = hashedPws
    }
    fun getHashPwd() = hashPassword
    fun getEmail() = email
    fun setEmail(em:String) {
        email = em
    }
}
