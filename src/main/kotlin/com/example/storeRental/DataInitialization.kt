package com.example.storeRental

import com.example.storeRental.domain.*
import com.example.storeRental.repo.*
import com.example.storeRental.utils.passwordEncoder.EncryptPwd
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitialization(private val userRepo: UserRepo, private val cusRepo: CustomerRepo,
                private val rentalRepo: RentalRepo, private val storeRepo: StoreRepo,
                private val rentalDetailRepo: RentalDetailRepo, private val storeImageRepo: StoreImageRepo,
                private val storeTypeRepo: StoreTypeRepo,
                private val roleRepo: RoleRepo
)
{
    @Bean
    fun dataInit() = ApplicationRunner{

        val admin = Role("ADMIN")
        val user =  Role("USER")
        roleRepo.saveAll(listOf(admin,user))
        val backob = User("Back Ko", "backko@mail.com",EncryptPwd().encryptPwd("123"), mutableSetOf(admin, user))
        val jack = User("Jack", "jack@mail.com",EncryptPwd().encryptPwd("123"), mutableSetOf(admin, user))
        val john = User("John", "john@mail.com",EncryptPwd().encryptPwd("123"), mutableSetOf(user))
        val thida = User("Thida", "thida@mail.com",EncryptPwd().encryptPwd("123"), mutableSetOf(user))
        userRepo.saveAll(listOf(backob, jack, john, thida))

        val storeT1 = StoreType(" Restaurant")
        val storeT2 = StoreType("Art Supplies")
        val storeT3 = StoreType("Gym ")
        val storeT4 = StoreType("Food")
        val storeT5 = StoreType("Beverages")
        storeTypeRepo.saveAll(listOf(storeT1, storeT2, storeT3, storeT4, storeT5))

        val store1 = Store(storeType = storeT1, unitPrice = 1200.99, floor = "3")
        val store2 = Store(storeType = storeT2, unitPrice = 1100.99, floor = "12")
        val store3 = Store(storeType = storeT4, unitPrice = 1000.99, floor = "8")
        val store4 = Store(storeType = storeT2, unitPrice = 566.99, floor = "8")
        val store5 = Store(storeType = storeT1, unitPrice = 444.99, floor = "12")
        val store6 = Store(storeType = storeT3, unitPrice = 1200.99, floor = "6B")
        val store7 = Store(storeType = storeT5, unitPrice = 800.99, floor = "12")
        val store8 = Store(storeType = storeT5, unitPrice = 566.99, floor = "8", rented = true)
        val store9 = Store(storeType = storeT4, unitPrice = 444.99, floor = "12")
        val store10 = Store(storeType = storeT1, unitPrice = 1200.99, floor = "6B")
        val store11 = Store(storeType = storeT2, unitPrice = 800.99, floor = "12")
        storeRepo.saveAll(listOf(store1,store2,store3,store4,store5,store6,store7,store8,store9,store10,store11))

        val img1 = StoreImage("/img/k.jpg",store1)
        storeImageRepo.save(img1)

        val cus1 = Customer(name = "bot", address = "btn", phone = "0465324")
        val cus2 = Customer(name = "roman", address = "fds", phone = "045656453")
        val cus3 = Customer(name = "dodo", address = "bsdftn", phone = "0898675")
        val cus4 =Customer(name = "jack", address = "b2234tn", phone = "05645434")
        val cus5 = Customer(name = "bobo", address = "dsf", phone = "04356354")
        val cus6 = Customer(name = "robert", address = "pp", phone = "09856354")
        cusRepo.saveAll(listOf(cus1,cus2,cus3,cus4,cus5, cus6))

        val rental1 = Rental(cus1) // 1cus -> many rentals
        val rental2 = Rental(cus1)
        val rental3 = Rental(cus5)
        val rental4 = Rental(cus2)
        val rental5 = Rental(cus4)
        val rental6 = Rental(cus2)
        val rental7 = Rental(cus3)
        val rental8 = Rental(cus3)
        rentalRepo.saveAll(listOf(rental1,rental2,rental3,rental4,rental5,rental6,rental7,rental8))

        val rentalDetails = listOf(
            RentalDetail(store1,rental1),//cus1(cus1 -> rental1) rents store 1 / 1 rental - many rental_detail
            RentalDetail(store3,rental1),//cus 1 rents store 3
            RentalDetail(store5,rental3),//cus 1 rents store 5
            RentalDetail(store6,rental4),
            RentalDetail(store2,rental2),
            RentalDetail(store4,rental6),
            RentalDetail(store9,rental7),
            RentalDetail(store8,rental8),
            RentalDetail(store8,rental2)
        )
        rentalDetailRepo.saveAll(rentalDetails)
    }
}