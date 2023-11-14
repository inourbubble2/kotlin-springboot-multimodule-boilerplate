package com.example.boilerplate.database.repository

import com.example.boilerplate.database.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<AccountEntity, Long>
