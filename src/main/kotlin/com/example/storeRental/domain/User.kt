package com.example.storeRental.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import kotlin.math.min


@Entity
@Table(name = "tbuser", indexes = [Index(name = "email_idx", columnList = "email")])
class User(
    @Column(length = 32)
    @Size(min = 4, max = 32)
    @NotNull
    var username: String,
    @Column(name = "email", length = 128)
    @Size(max = 128)
    @NotNull
    var email: String,
    private var hashPassword:String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
        joinColumns = [JoinColumn(name = "userId", foreignKey = ForeignKey(name = "fk_user_id"))],
        inverseJoinColumns = [JoinColumn(name = "roleId", foreignKey = ForeignKey(name = "fk_role_id"))])
    var roles:MutableSet<Role>,
    var is_verified:Boolean? = false,
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    var token:UserVerificationToken? = null
): BaseModel(){
    fun getHashPwd() = hashPassword
}
