package com.example.storeRental.service

import com.example.storeRental.domain.Role
import com.example.storeRental.repo.RoleRepo
import com.example.storeRental.utils.passwordEncoder.EncryptPwd
import com.example.storeRental.repo.UserRepo
import com.example.storeRental.utils.requestClass.UserLoginRequest
import com.example.storeRental.utils.requestClass.UserRegisterRequest
import com.example.storeRental.utils.dto.UserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class UserService(private val userRepo: UserRepo,private val roleRepo: RoleRepo):BaseSevice<com.example.storeRental.domain.User, Long>{

    override fun save(model: com.example.storeRental.domain.User) {
        if(model != null)
            userRepo.save(model)
    }

    fun getUserById(id: Long): UserDto {
        val userRes = userRepo.findById(id).orElse(null)
        return UserDto(userRes.username, userRes.email, userRes.getHashPwd())
    }

    override fun deleteById(id: Long) {
        val user = userRepo.findById(id).orElse(null)
        if(user != null){
            userRepo.delete(user)
        }
    }

    fun getAllUser(jwt:String?):Any?{
        jwt?: return "Unauthenticated"
        try {
            Jwts.parser().setSigningKey("fadfasdfsd").parseClaimsJws(jwt)
        }catch(e:Exception){
            return e.message
        }
        return userRepo.findAll()
    }

    fun register(userRes: UserRegisterRequest): Boolean{

        if(userRes.username.length > 5 &&
            userRes.password.length > 8
        ){
            val role = roleRepo.findById(1).orElse(null)
            val newUser = com.example.storeRental.domain.User(userRes.username, userRes.email, EncryptPwd().encryptPwd(userRes.password),
                mutableSetOf(role))

            userRepo.save(newUser)
            return true
        }
        return false
    }

    fun login(userRes: UserLoginRequest, response:HttpServletResponse): UserDto?{
        if(userRes.password.length > 8)
        {
            val user = userRepo.findByEmail(userRes.email).orElse(null)
            if(user != null)
                if(!BCrypt.checkpw(userRes.password, user.getHashPwd()))
                    return null
            val jwt = Jwts.builder()
                .setClaims(mapOf("username" to user.username, "id" to user.id, "email" to user.email))
                .setExpiration(Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS512, "fadfasdfsd")
                .compact()
            val cookie = Cookie("jwt", jwt)
            response.addCookie(cookie)
            return UserDto(user.username,user.email,user.getHashPwd())
        }
        return null
    }

    override fun getById(id: Long): com.example.storeRental.domain.User? {
        return userRepo.findById(id).orElse(null)
    }

}
