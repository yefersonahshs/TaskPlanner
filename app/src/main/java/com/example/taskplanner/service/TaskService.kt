package com.example.taskplanner.service

import com.example.taskplanner.service.dto.TaskPlannerDto
import com.example.taskplanner.service.dto.TaskTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {

    @POST("/auth")
    suspend fun authTaskPlanner(@Body userData: UserInfo): Response<TaskTokenDto>


    @GET("/api/task/all")
    suspend fun getTaskList(): Response<TaskPlannerDto>


}