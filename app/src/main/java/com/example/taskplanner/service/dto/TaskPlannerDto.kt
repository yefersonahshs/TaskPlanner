package com.example.taskplanner.service.dto

import com.example.taskplanner.data.entity.Task
import com.google.gson.annotations.SerializedName
import java.util.*

data class TaskPlannerDto(

    @SerializedName("id"          ) var id: String?,
    @SerializedName("name"        ) var name: String,
    @SerializedName("description" ) var description: String,
    @SerializedName("status"      ) var status: StatusEnum ,
    @SerializedName("assignedTo"  ) var assignedTo: String,
    @SerializedName("dueDate"     ) var dueDate:  Date

){
    constructor(task: Task) : this(
        null,
        task.name,
        task.description,
        StatusEnum.valueOf(task.status),
        task.assignedTo,
        task.dueDate
    )
}