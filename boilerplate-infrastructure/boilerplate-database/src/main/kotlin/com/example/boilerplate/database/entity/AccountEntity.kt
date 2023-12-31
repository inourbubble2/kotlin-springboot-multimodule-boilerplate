package com.example.boilerplate.database.entity

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
class AccountEntity(
    @Column(name = "name")
    val name: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: AccountStatus,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val accountId: Long = 0L
) {
    fun convertToDomain() = Account(accountId, name, email, status)
}
