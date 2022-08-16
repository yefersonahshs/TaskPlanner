package com.example.taskplanner.storage

interface Storage {

    fun saveToken(token: String)

    fun getToken(): String

    fun clear()

    fun isUserAuth(): Boolean
}