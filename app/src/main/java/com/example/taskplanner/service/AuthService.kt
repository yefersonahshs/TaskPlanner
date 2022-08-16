package com.example.taskplanner.service


import com.example.taskplanner.service.dto.LoginDto
import com.example.taskplanner.service.dto.TaskTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Santiago Carrillo
 * 19/10/21.
 */
interface AuthService {

    @POST("auth")
    suspend fun auth(@Body loginDto: LoginDto): Response<TaskTokenDto>

}