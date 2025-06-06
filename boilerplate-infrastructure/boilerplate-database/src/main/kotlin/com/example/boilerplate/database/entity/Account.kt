package com.example.boilerplate.database.entity

import com.example.boilerplate.database.model.AccountStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "account")
class Account(
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
    val accountId: Long = 0L,
)
