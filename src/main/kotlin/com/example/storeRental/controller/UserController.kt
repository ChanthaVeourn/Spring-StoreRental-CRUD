package com.example.storeRental.controller

import com.example.storeRental.domain.UserModel
import com.example.storeRental.utils.UserLoginDataResponse
import com.example.storeRental.utils.UserRegisterDataResponse
import com.example.storeRental.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("api/user")
class UserController(private var userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id:Long):ResponseEntity<UserModel>{
        val user =  userService.getById(id)
        return ResponseEntity.ok().body(user)
    }

    @GetMapping
    fun getAllUSer():ResponseEntity<Iterable<UserModel>>{
        return ResponseEntity.ok().body(userService.getAllUser())
    }

    @PostMapping("/register")
    fun register(@RequestBody userResource: UserRegisterDataResponse): ResponseEntity<String>{
        if(userService.register(userResource))
            return ResponseEntity.status(201).build()
        return ResponseEntity.badRequest().body("Invalid provided information.")
    }

    @PostMapping("/login")
    fun login(@RequestBody userResource: UserLoginDataResponse):ResponseEntity<String>{
        if(userService.login(userResource)){
            return ResponseEntity.ok().body("Logged-in User")
        }
        return ResponseEntity.status(403).body("Invalid or incorrect email or password")
    }

}