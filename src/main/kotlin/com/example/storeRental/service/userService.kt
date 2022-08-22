package com.example.storeRental.service

import com.example.storeRental.domain.User
import com.example.storeRental.repo.RoleRepo
import com.example.storeRental.utils.passwordEncoder.EncryptPwd
import com.example.storeRental.repo.UserRepo
import com.example.storeRental.utils.requestClass.UserLoginRequest
import com.example.storeRental.utils.requestClass.UserRegisterRequest
import com.example.storeRental.dto.UserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class UserService(private val userRepo: UserRepo,private val roleRepo: RoleRepo,
                  @Value("\${jwt_secret_key}")private val jwt_secret:String):BaseSevice<User, Long>{

    override fun save(model: User) {
        if(model != null)
            userRepo.save(model)
    }

    fun getUserById(id: Long): UserDto? {
        val user = userRepo.findById(id)
        if(user.isPresent){
            val userRes = user.get()
            return UserDto(userRes.username, userRes.email, userRes.getHashPwd())
        }
        return null
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
            Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(jwt)
        }catch(e:Exception){
            return e.message
        }
        return userRepo.findAll()
    }

    fun register(userRes: UserRegisterRequest): Boolean{

        if(userRes.username.length > 5 &&
            userRes.password.length > 8
        ){
            val role = roleRepo.findById(1).get()
            val newUser = User(userRes.username, userRes.email, EncryptPwd().encryptPwd(userRes.password),
                mutableSetOf(role))

            userRepo.save(newUser)
            return true
        }
        return false
    }

    fun login(userRes: UserLoginRequest, response:HttpServletResponse): UserDto?{
        if(userRes.password.length > 8)
        {
            println(jwt_secret)
            val user = userRepo.findByEmail(userRes.email).orElse(null)
            if(user != null)
                if(!BCrypt.checkpw(userRes.password, user.getHashPwd()))
                    return null
            val jwt = Jwts.builder()
                .setClaims(mapOf("username" to user.username, "id" to user.id, "email" to user.email))
                .setExpiration(Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS512, jwt_secret)
                .compact()
            val cookie = Cookie("jwt", jwt)
            response.addCookie(cookie)
            return UserDto(user.username,user.email,user.getHashPwd())
        }
        return null
    }

    override fun getById(id: Long): User? {
        return userRepo.findById(id).orElse(null)
    }

}
