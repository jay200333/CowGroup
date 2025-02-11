package com.example.model

data class SignUpInfo(
    val username: String,
    val gender: Gender,
    val email: String,
    val password: String,
)

enum class Gender(
    val label: String,
) {
    MALE("남성"),
    FEMALE("여성"),
}
