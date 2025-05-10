package com.example.model

data class DetailEvent(
    val id: Int,
    val name: String,
    val category: String,
    val content: String,
    val capacity: Int,
    val applicants: Int,
    val isBookmarked: Boolean,
    val url: String?,
    val eventRegistrant: Boolean,
    val isParticipated: Boolean,
    val regularEvents: List<RegularEvent>
)
