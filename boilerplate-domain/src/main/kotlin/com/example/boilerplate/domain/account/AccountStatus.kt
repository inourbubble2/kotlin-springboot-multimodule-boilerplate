package com.example.boilerplate.domain.account

enum class AccountStatus(
    val desc: String,
) {
    ACTIVE("활성"),
    DORMANT("휴면"),
    DELETED("탈퇴"),
}
