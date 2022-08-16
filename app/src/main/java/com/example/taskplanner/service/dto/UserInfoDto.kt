package com.example.taskplanner.service

import com.google.gson.annotations.SerializedName


data class UserInfo (
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null
)