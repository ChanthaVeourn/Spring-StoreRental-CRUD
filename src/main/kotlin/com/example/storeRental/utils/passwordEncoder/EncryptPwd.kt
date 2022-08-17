package com.example.storeRental.utils.passwordEncoder

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class EncryptPwd {
    fun encryptPwd(rawPassword: String):String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }

}