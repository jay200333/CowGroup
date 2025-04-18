package com.example.network.model

import com.google.gson.annotations.SerializedName

data class CheckDuplicateResponse(
    @SerializedName("verificationPassed")
    val verificationPassed: Boolean,
)
