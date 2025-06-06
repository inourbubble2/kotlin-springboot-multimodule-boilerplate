package com.example.boilerplate.database.repository

import com.example.boilerplate.database.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>
