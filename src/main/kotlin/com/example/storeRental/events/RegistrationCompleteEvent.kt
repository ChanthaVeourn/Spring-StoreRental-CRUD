package com.example.storeRental.events

import com.example.storeRental.domain.User
import org.springframework.context.ApplicationEvent

class RegistrationCompleteEvent(val user: User, val verificationUrl:String,val token:String): ApplicationEvent(user)