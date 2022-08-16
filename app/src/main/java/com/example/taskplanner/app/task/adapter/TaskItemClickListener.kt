package org.adaschool.taskplanner.ui.task.adapter

import com.example.taskplanner.data.entity.Task

interface TaskItemClickListener {

    fun onTaskClicked(task: Task)
}