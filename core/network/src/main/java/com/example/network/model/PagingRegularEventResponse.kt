package com.example.network.model

import com.example.model.RegularEvent
import com.google.gson.annotations.SerializedName

data class PagingRegularEventResponse(
    val regularEventInfos: List<RegularEventInfo>,
    val hasNext: Boolean
)

data class RegularEventInfo(
    val id: Int,
    val participationId: Int?,
    val name: String,
    val dateTime: String,
    val location: String,
    val participantsCount: Int,
    val capacity: Int,
    @SerializedName("regularRegistrant")
    val isRegularRegistrant: Boolean,
)

fun RegularEventInfo.toRegularEvent(): RegularEvent {
    return RegularEvent(
        id = id,
        participationId = participationId ?: 0,
        name = name,
        dateTime = dateTime,
        location = location,
        applicants = participantsCount,
        capacity = capacity,
        isRegularRegistrant = isRegularRegistrant,
    )
}
