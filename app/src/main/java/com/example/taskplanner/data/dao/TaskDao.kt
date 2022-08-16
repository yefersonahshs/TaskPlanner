package com.example.taskplanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskplanner.data.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * from task")
    fun all(): LiveData<List<Task>>

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * from task WHERE id = :id")
    fun findById(id: String): Task?

    @Query("SELECT * from task WHERE id IS NULL")
    fun findNotUploadedTasks(): List<Task>
}