package com.example.network.model

import com.example.model.Comment

data class CommentResponse(
    val comments: List<CommentInfo>
)

data class CommentInfo(
    val id: Int,
    val username: String,
    val daysAgo: String,
    val content: String,
    val registrant: Boolean
)

fun CommentInfo.toComment(): Comment =
    Comment(
        id = id,
        username = username,
        daysAgo = daysAgo,
        content = content,
        isRegistrant = registrant
    )

