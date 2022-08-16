package com.example.taskplanner.service.dto

import com.google.gson.annotations.SerializedName

data class TaskTokenDto (
    @SerializedName("accessToken" ) var accessToken : String? = null
)
