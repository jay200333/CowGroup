package com.example.network.model

data class PagingEventResponse(
    val content: List<NetworkEvent>,
    val last: Boolean
)