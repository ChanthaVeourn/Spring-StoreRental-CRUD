package com.example.storeRental.domain

import javax.persistence.*


@Entity
@Table(name = "tbuser")
class User(
    @Column(name = "username")
    var username: String,
    @Column(name = "email")
    var email: String,
    private var hashPassword:String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole", joinColumns = [JoinColumn(name = "userId")], inverseJoinColumns = [JoinColumn(name = "roleId")])
    var roles:MutableSet<Role>
): BaseModel(){
    fun getHashPwd() = hashPassword
}
