package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpInfo(
    val username: String,
    val email: String,
    val password: String,
    val code: String,
    val gender: Gender,
    val mbti: MBTI
)

@Serializable
data class SignUpStep1Info(
    val username: String,
    val email: String,
    val authCode: String,
    val password: String
)

enum class Gender(
    val label: String,
) {
    MALE("남성"),
    FEMALE("여성");

    companion object {
        fun fromLabel(label: String): Gender? {
            return Gender.entries.find { it.label == label }
        }
    }
}

enum class MBTI {
    INTJ, INTP, INFJ, INFP,
    ISTJ, ISTP, ISFJ, ISFP,
    ENTJ, ENTP, ENFJ, ENFP,
    ESTJ, ESTP, ESFJ, ESFP,
}
