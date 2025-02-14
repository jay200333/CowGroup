package com.example.network.model

import com.example.model.Profile

data class ProfileResponse(
    val username: String,
    val introduction: String?,
    val localName: String?,
    val birth: String?,
    val mbti: String?,
)

fun ProfileResponse.toProfile(): Profile = Profile(
    username = username,
    introduction = introduction?:"",
    localName = localName?:"",
    birth = birth?:"",
    mbti = mbti?:"INTJ",
)