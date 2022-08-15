package com.example.storeRental.utils

import org.springframework.security.crypto.bcrypt.BCrypt

class EncryptPwd {
    fun encryptPwd(rawPassword: String):String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }
}