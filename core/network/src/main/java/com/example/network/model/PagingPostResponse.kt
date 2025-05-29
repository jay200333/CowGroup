package com.example.network.model

import com.example.model.Post
import com.google.gson.annotations.SerializedName

data class PagingPostResponse(
    val postInfos: List<PostInfo>,
    val hasNext: Boolean
)

data class PostInfo(
    val id: Int,
    @SerializedName("subject")
    val title: String,
    val content: String,
    val createdDateTime: String,
    val username: String,
    val commentCount: Int,
    @SerializedName("registrant")
    val isRegistrant: Boolean
)

fun PostInfo.toPost(): Post {
    return Post(
        id = id,
        title = title,
        content = content,
        dateTime = createdDateTime,
        userName = username,
        commentCount = commentCount,
        isRegistrant = isRegistrant
    )
}
