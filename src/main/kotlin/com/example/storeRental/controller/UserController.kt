package com.example.storeRental.controller

import com.example.storeRental.events.RegistrationCompleteEvent
import com.example.storeRental.utils.requestClass.UserLoginRequest
import com.example.storeRental.utils.requestClass.UserRegisterRequest
import com.example.storeRental.service.UserService
import com.example.storeRental.utils.responseClass.ResponseData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
@RequestMapping("api/user")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id:Long):ResponseEntity<ResponseData>{
        val user =  userService.getUserById(id)
        user?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(ResponseData(statusCode = 200, data = user))
    }

    @GetMapping
    fun getAllUSer(@CookieValue("jwt") jwt: String?):ResponseEntity<Any>{
        val res = userService.getAllUser(jwt)
        if(res !is String)
            return ResponseEntity.ok(res)
        return ResponseEntity.status(401).body(res)
    }

    @PostMapping("/register")
    fun register(@RequestBody userResource: UserRegisterRequest,httpReq:HttpServletRequest, httpRes:HttpServletResponse): ResponseEntity<Any>{
        val newUser = userService.register(userResource, httpRes, httpReq)
        if(newUser != null){
            return ResponseEntity.status(201).body(newUser)
        }
        return ResponseEntity.badRequest().body("Incorrect provided information.")
    }

    @PostMapping("/verify-request")
    fun verifyRequest(@RequestParam email:String, request: HttpServletRequest):ResponseEntity<Any>{
        userService.verifyRequest(email, request)?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(userService.verifyRequest(email, request))
    }

    @PostMapping("/verify-registration")
    fun verifyUserRegistration(@RequestParam email:String,@RequestParam token:String, response:HttpServletResponse):ResponseEntity<Any>{
        val user = userService.findUserByEmail(email)
        user?: return ResponseEntity.notFound().build()
        println(user.email)
        if(userService.verifyRegistrationUser(user, token, response))
            return ResponseEntity.accepted().build()
        return ResponseEntity.badRequest().build()
    }


    @PostMapping("/login")
    fun login(@RequestBody userResource: UserLoginRequest, response: HttpServletResponse):ResponseEntity<Any>{

        val res = userService.login(userResource, response)
        res?: return ResponseEntity.status(403).body("Invalid or incorrect email or password")
        return ResponseEntity.ok(res)
    }


}