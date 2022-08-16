package com.example.taskplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskplanner.service.dto.TaskPlannerDto
import java.util.*


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    var id: String?,
    var name: String,
    var description: String,
    var assignedTo: String,
    var dueDate: Date,
    var status: String
) {
    constructor(taskDto: TaskPlannerDto) : this(
        null,
        taskDto.id,
        taskDto.name,
        taskDto.description,
        taskDto.assignedTo,
        taskDto.dueDate,
        taskDto.status.name
    )

    fun update(taskDto: TaskPlannerDto) {
        name = taskDto.name
        description = taskDto.description
        assignedTo = taskDto.assignedTo
        dueDate = taskDto.dueDate
    }

}

