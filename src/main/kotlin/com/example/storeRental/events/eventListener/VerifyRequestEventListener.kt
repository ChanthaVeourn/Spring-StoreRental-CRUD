package com.example.storeRental.events.eventListener

import com.example.storeRental.events.VerifyRequestEvent
import com.example.storeRental.repo.UserTokenRepo
import com.example.storeRental.service.UserService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class VerifyRequestEventListener(private val userService: UserService, private val userTokenRepo: UserTokenRepo):
    ApplicationListener<VerifyRequestEvent> {

    override fun onApplicationEvent(event: VerifyRequestEvent) {
        val user = event.user
        val url = event.verificationUrl
        val token = user.token?.token
        val verifyUrl = "$url/verify-registration?token=$token&email=${user.email}"
        //send verification link
        println("Verification link is $verifyUrl")
    }

}
