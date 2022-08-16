package com.example.taskplanner.app.task.adapter

import com.example.taskplanner.data.entity.Task

interface TaskItemClickListener {

    fun onTaskClicked(task: Task)
}