package com.example.storeRental.events.eventListener

import com.example.storeRental.domain.User
import com.example.storeRental.domain.UserVerificationToken
import com.example.storeRental.events.RegistrationCompleteEvent
import com.example.storeRental.repo.UserTokenRepo
import com.example.storeRental.service.UserService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class RegistrationCompleteEventListener(private val userService: UserService, private val userTokenRepo: UserTokenRepo): ApplicationListener<RegistrationCompleteEvent> {

    override fun onApplicationEvent(event: RegistrationCompleteEvent) {
        val user = event.user
        val url = event.verificationUrl
        val token = event.token
        val verifyUrl = "$url/verify-registration?token=$token&email=${user.email}"

        //wait for saving completed then send email
        val userToken = UserVerificationToken(token = token, user = user)
        user.token = userToken
        userService.save(user)
/*        send this verification link to user to verify that this is the email or ...'s owner and currently using this email
        when click -> verify token with database*/
        println("Verify your email vai this link: $verifyUrl")
    }

}