package com.example.storeRental

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StoreRentalWebApplicationTests{

	@BeforeAll
	fun `testing endpiont`(){
		println("setup testing")
	}

	@Test
	fun `getting all user`() {
	}
	@AfterAll
	fun teardown(){
		println("test completed")
	}
}
