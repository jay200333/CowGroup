package com.example.network.model

import com.google.gson.annotations.SerializedName

data class PagingBookmarkEventResponse(
    @SerializedName("bookmarks")
    val bookmarkEvents: List<NetworkEvent>
)

