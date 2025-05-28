package com.example.network.model

import com.example.model.EventMember
import com.example.model.MemberInfo
import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName("eventParticipants")
    val participants: List<ParticipantInfo>,
    val participantCount: Int
)

data class RegularEventMemberResponse(
    @SerializedName("regularParticipants")
    val participants: List<ParticipantInfo>,
    val participantCount: Int
)

data class ParticipantInfo(
    val name: String,
    val mbti: String,
    //val profileURI: URI? = null
)

fun MemberResponse.toEventMember(): EventMember = EventMember(
    memberInfoList = participants.map { it.toMemberInfo() },
    memberCount = participantCount
)

fun RegularEventMemberResponse.toEventMember(): EventMember = EventMember(
    memberInfoList = participants.map { it.toMemberInfo() },
    memberCount = participantCount
)

fun ParticipantInfo.toMemberInfo(): MemberInfo = MemberInfo(
    name = name,
    mbti = mbti,
    //profileUri = profileURI
)
