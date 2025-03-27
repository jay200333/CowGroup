package com.example.network.model

data class PagingHomeEventResponse(
    val content: List<NetworkEvent>,
    val last: Boolean
)