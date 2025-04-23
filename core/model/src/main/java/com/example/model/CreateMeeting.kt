package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeeting(
    val name: String,
    val category: Category,
    val capacity: Int,
    val content: String,
    val file: String
)

enum class Category(
    val label: String
) {
    SPORTS("운동"), TRAVEL("여행"), READING("독서"), RESTAURANT("맛집"),
    PERFORMANCE("공연"), PICTURE("사진"), SELF_DEVELOPMENT("자기계발"), HOBBY("취미"),
    PET("반려동물"), GAME("게임"), SERVICE("봉사"), REMAIN("기타");

    companion object {
        fun fromLabel(label: String): Category? {
            return entries.find { it.label == label }
        }
    }
}