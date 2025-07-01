package com.example.network.model

data class PagingHomeEventResponse(
    val eventSearchInfos: List<NetworkEvent>,
    val hasNext: Boolean
)