package com.example.network.model

import com.google.gson.annotations.SerializedName

data class CheckVerificationResponse(
    @SerializedName("verificationPassed")
    val verificationPassed: Boolean,
)
