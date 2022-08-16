package com.example.taskplanner.service

import com.example.taskplanner.service.dto.TaskPlannerDto
import com.example.taskplanner.service.dto.TaskTokenDto
import retrofit2.Response
import retrofit2.http.*

interface TaskService {

    @POST("/auth")
    suspend fun authTaskPlanner(@Body userData: UserInfo): Response<TaskTokenDto>


    @GET("/api/task/all")
    suspend fun getTaskList(): Response<List<TaskPlannerDto>>

    @POST("api/task")
    suspend fun saveTask(@Body taskDto: TaskPlannerDto): Response<TaskPlannerDto>

    @PUT("api/task/{id}")
    suspend fun updateTask(@Body taskDto: TaskPlannerDto, @Path("id") id: String): Response<TaskPlannerDto>


}