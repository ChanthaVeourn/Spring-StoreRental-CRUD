package com.example.storeRental.controller

import com.example.storeRental.utils.requestClass.UserLoginRequest
import com.example.storeRental.utils.requestClass.UserRegisterRequest
import com.example.storeRental.service.UserService
import com.example.storeRental.utils.dto.UserDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@Controller
@RequestMapping("api/user")
class UserController(private var userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id:Long):ResponseEntity<UserDto>{
        val user =  userService.getUserById(id)
        return ResponseEntity.ok().body(user)
    }

    @GetMapping
    fun getAllUSer(@CookieValue("jwt") jwt: String?):ResponseEntity<Any>{
        val res = userService.getAllUser(jwt)
        if(res !is String)
            return ResponseEntity.ok(res)
        return ResponseEntity.status(401).body(res)
    }

    @PostMapping("/register")
    fun register(@RequestBody userResource: UserRegisterRequest): ResponseEntity<String>{
        if(userService.register(userResource))
            return ResponseEntity.status(201).build()
        return ResponseEntity.badRequest().body("Invalid provided information.")
    }

    @PostMapping("/login")
    fun login(@RequestBody userResource: UserLoginRequest, response: HttpServletResponse):ResponseEntity<Any>{

        val res = userService.login(userResource, response)
        res?: return ResponseEntity.status(403).body("Invalid or incorrect email or password")
        return ResponseEntity.ok(res)
    }

}