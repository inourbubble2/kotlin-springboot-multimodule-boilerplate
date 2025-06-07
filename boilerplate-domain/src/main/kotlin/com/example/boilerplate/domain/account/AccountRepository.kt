package com.example.boilerplate.domain.account

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun existsByName(name: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findByNameAndEmail(
        name: String,
        email: String,
    ): Account?
}
