package com.example.storeRental.service

import com.example.storeRental.domain.User
import com.example.storeRental.domain.UserVerificationToken
import com.example.storeRental.repo.RoleRepo
import com.example.storeRental.utils.passwordEncoder.EncryptPwd
import com.example.storeRental.repo.UserRepo
import com.example.storeRental.utils.requestClass.UserLoginRequest
import com.example.storeRental.utils.requestClass.UserRegisterRequest
import com.example.storeRental.dto.UserDto
import com.example.storeRental.events.RegistrationCompleteEvent
import com.example.storeRental.events.VerifyRequestEvent
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class UserService(private val userRepo: UserRepo, private val roleRepo: RoleRepo,
                  @Value("\${jwt_secret_key}")private val jwt_secret:String,
                  private val publisher: ApplicationEventPublisher
):BaseSevice<User, Long>{

    override fun save(model: User) {
            userRepo.save(model)
    }

    fun findUserByEmail(email:String):User?{
       return userRepo.findByEmail(email).orElse(null)
    }
    fun findById(id: Long):User?{
       return userRepo.findById(id).orElse(null)
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

    fun register(userRes: UserRegisterRequest, response:HttpServletResponse, request: HttpServletRequest): User?{

        if(userRes.username.length > 5 &&
            userRes.password.length > 8
        ){
            val role = roleRepo.findById(2).get()
            val newUser = User(userRes.username, userRes.email, EncryptPwd().encryptPwd(userRes.password),
                mutableSetOf(role))
            val jwt = createToken(mapOf("username" to newUser.username, "id" to newUser.id, "email" to newUser.email), jwt_secret)
            publisher.publishEvent(RegistrationCompleteEvent(newUser,verificationUrl(request), token = jwt))
            return newUser
        }
        return null
    }

    fun verifyRequest(email:String, request:HttpServletRequest):UserDto?{
        val reqUser:User? = userRepo.findByEmail(email).orElse(null)
        reqUser?: return null
        val jwt = createToken(mapOf("username" to reqUser.username, "id" to reqUser.id, "email" to reqUser.email), jwt_secret)

        if(reqUser.token == null) {
            val userToken = UserVerificationToken(token = jwt, user = reqUser)
            reqUser.token = userToken
        }
        else reqUser.token!!.token = jwt
        userRepo.save(reqUser)

        publisher.publishEvent(VerifyRequestEvent(reqUser,verificationUrl(request)))
        return UserDto(reqUser.username,reqUser.email)
    }

    fun verifyRegistrationUser(user:User, token:String,response:HttpServletResponse):Boolean{
        if(token == user.token?.token)
        {
            user.is_verified = true
            val jwt = refreshToken(mapOf("username" to user.username,
                "id" to user.id,
                "email" to user.email), jwt_secret)
            user.token!!.token = jwt
            userRepo.save(user)
            val jwtCookie = Cookie("jwt", jwt)
            response.addCookie(jwtCookie)
            return true
        }
        return false
    }

    fun login(userRes: UserLoginRequest, response:HttpServletResponse): UserDto?{
        if(userRes.password.length > 8)
        {
            val user = userRepo.findByEmail(userRes.email).orElse(null)
            if(user != null){
                if(!BCrypt.checkpw(userRes.password, user.getHashPwd()))
                    return null
                val jwt = createToken(mapOf("username" to user.username, "id" to user.id, "email" to user.email), jwt_secret)
                user.token?.token = jwt
                userRepo.save(user)
                val cookie = Cookie("jwt", jwt)
                response.addCookie(cookie)
                return UserDto(user.username,user.email,user.getHashPwd())
            }
        }
        return null
    }

    override fun getById(id: Long): User? {
        return userRepo.findById(id).orElse(null)
    }

    fun createToken(claims: Map<String, Any?>, jwt_secret:String ): String {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, jwt_secret)
            .compact()
    }

    fun refreshToken(claims: Map<String, Any?>, jwt_secret:String ): String {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, jwt_secret)
            .compact()
    }

    fun verificationUrl(http: HttpServletRequest):String{
        return "http://${http.serverName}:${http.serverPort}/api/user"
    }
}


