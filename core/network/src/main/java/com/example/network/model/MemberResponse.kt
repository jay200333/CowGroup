package com.example.network.model

import com.example.model.MemberInfo

data class MemberResponse(
    val participants: List<ParticipantInfo>
)

data class ParticipantInfo(
    val name: String,
    val gender: String
)

fun ParticipantInfo.toMemberInfo(): MemberInfo = MemberInfo(
    name = name,
    gender = gender
)
