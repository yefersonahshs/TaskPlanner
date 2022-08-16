package com.example.taskplanner.storage

import android.content.SharedPreferences
import com.example.taskplanner.util.TOKEN_KEY


class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) : Storage {


    override fun saveToken(token: String) {
        if (token == "exception") {
            throw RuntimeException()
        }
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun getToken(): String {
        return sharedPreferences.getString(TOKEN_KEY, "")!!
    }

    override fun clear() {
        return sharedPreferences.edit()
            .remove(TOKEN_KEY)
            .apply()
    }

    override fun isUserAuth(): Boolean {
        return getToken().isNotEmpty()
    }

}