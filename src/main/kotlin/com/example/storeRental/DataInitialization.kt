package com.example.storeRental

import com.example.storeRental.domain.*
import com.example.storeRental.repo.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitialization(private val userRepo: UserRepo, private val cusRepo: CustomerRepo,
                private val rentalRepo: RentalRepo, private val storeRepo: StoreRepo,
                private val rentalDetailRepo: RentalDetailRepo, private val storeImageRepo: StoreImageRepo,
                private val storeTypeRepo: StoreTypeRepo
)
{
    @Bean
    fun dataInit() = ApplicationRunner{

        val backob = UserModel("Back Ko", "backko@mail.com")
        backob.setPassword("123")

        val jack = UserModel("Jack", "jack@mail.com")
        jack.setPassword("1223")

        val john = UserModel("John", "john@mail.com")
        john.setPassword("sdsd")

        val thida = UserModel("thida", "thida@mail.com")
        thida.setPassword("asf")
        userRepo.saveAll(listOf(backob, jack, john, thida))

        val storeT1 = StoreTypeModel(" Restaurant")
        val storeT2 = StoreTypeModel("Art Supplies")
        val storeT3 = StoreTypeModel("Gym ")
        val storeT4 = StoreTypeModel("Food")
        val storeT5 = StoreTypeModel("Beverages")
        storeTypeRepo.saveAll(listOf(storeT1, storeT2, storeT3, storeT4, storeT5))

        val store1 = StoreModel(storeType = storeT1, unitPrice = 1200.99, floor = "3")
        val store2 = StoreModel(storeType = storeT2, unitPrice = 1100.99, floor = "12")
        val store3 = StoreModel(storeType = storeT4, unitPrice = 1000.99, floor = "8")
        val store4 = StoreModel(storeType = storeT2, unitPrice = 566.99, floor = "8")
        val store5 = StoreModel(storeType = storeT1, unitPrice = 444.99, floor = "12")
        val store6 = StoreModel(storeType = storeT3, unitPrice = 1200.99, floor = "6B")
        val store7 = StoreModel(storeType = storeT5, unitPrice = 800.99, floor = "12")
        val store8 = StoreModel(storeType = storeT5, unitPrice = 566.99, floor = "8")
        val store9 = StoreModel(storeType = storeT4, unitPrice = 444.99, floor = "12")
        val store10 = StoreModel(storeType = storeT1, unitPrice = 1200.99, floor = "6B")
        val store11 = StoreModel(storeType = storeT2, unitPrice = 800.99, floor = "12")
        storeRepo.saveAll(listOf(store1,store2,store3,store4,store5,store6,store7,store8,store9,store10,store11))

        val cus1 = CustomerModel(name = "bot", address = "btn", phone = "0465324")
        val cus2 = CustomerModel(name = "asd", address = "fds", phone = "045656453")
        val cus3 = CustomerModel(name = "bdfot", address = "bsdftn", phone = "0898675")
        cusRepo.save(cus1)
        cusRepo.save(cus2)
        cusRepo.save(cus3)
        cusRepo.save(CustomerModel(name = "wre", address = "b2234tn", phone = "05645434"))
        cusRepo.save(CustomerModel(name = "kj", address = "dsf", phone = "04356354"))

        val rental1 = RentalModel(cus1)
        val rental2 = RentalModel(cus1)
        val rental3 = RentalModel(cus1)
        val rental4 = RentalModel(cus2)
        val rental5 = RentalModel(cus2)
        val rental6 = RentalModel(cus2)
        val rental7 = RentalModel(cus3)
        val rental8 = RentalModel(cus3)
        rentalRepo.saveAll(listOf(rental1,rental2,rental3,rental4,rental5,rental6,rental7,rental8))

        val rentalDetails = listOf(
            RentalDetailModel(store1,rental1),
            RentalDetailModel(store3,rental2),
            RentalDetailModel(store5,rental3),
            RentalDetailModel(store6,rental4),
            RentalDetailModel(store2,rental5),
            RentalDetailModel(store4,rental6),
            RentalDetailModel(store7,rental7),
            RentalDetailModel(store8,rental8)
        )
        rentalDetailRepo.saveAll(rentalDetails)
    }
}