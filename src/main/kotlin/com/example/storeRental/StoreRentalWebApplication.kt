package com.example.storeRental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.view.InternalResourceViewResolver
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableSwagger2
class StoreRentalWebApplication{
	@Bean
	fun defaultViewResolver(): InternalResourceViewResolver? {
		return InternalResourceViewResolver()
	}

}

fun main(args: Array<String>) {
	runApplication<StoreRentalWebApplication>(*args)
}
