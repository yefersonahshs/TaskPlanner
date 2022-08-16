package com.example.taskplanner.app.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.service.AuthService
import com.example.taskplanner.service.dto.LoginDto
import com.example.taskplanner.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val storage: Storage
) : ViewModel() {

    val labelLiveData = MutableLiveData<String>()

    val requestResultLiveData = MutableLiveData<Boolean>()

    fun auth(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authService.auth(LoginDto(email, password))

            if (response.isSuccessful) {
                storage.saveToken(response.body()?.accessToken!!)
            }
            requestResultLiveData.postValue(response.isSuccessful)
        }
    }


}