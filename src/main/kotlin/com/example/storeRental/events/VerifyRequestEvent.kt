package com.example.storeRental.events

import com.example.storeRental.domain.User
import org.springframework.context.ApplicationEvent

class VerifyRequestEvent(val user: User, val verificationUrl:String): ApplicationEvent(user)