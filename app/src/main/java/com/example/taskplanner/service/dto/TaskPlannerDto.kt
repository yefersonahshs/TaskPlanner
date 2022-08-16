package com.example.taskplanner.service.dto

import com.google.gson.annotations.SerializedName

data class TaskPlannerDto (

    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("status"      ) var status      : String? = null,
    @SerializedName("assignedTo"  ) var assignedTo  : String? = null,
    @SerializedName("dueDate"     ) var dueDate     : String? = null

)