package com.example.taskplanner.app.task.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.data.entity.Task
import com.example.taskplanner.service.TaskService
import com.example.taskplanner.service.dto.TaskPlannerDto
import com.example.taskplanner.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskService: TaskService,
    private val taskDao: TaskDao,
    val storage: Storage,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDao.all()

    var selectedTask: Task? = null

    val selectedDateLiveData: MutableLiveData<Date> = MutableLiveData()
    val saveTaskResult: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadTaskList()
        selectedDateLiveData.postValue(Date())
    }

    private fun loadTaskList() {
        viewModelScope.launch(dispatcher) {
            try {
                val response = taskService.getTaskList()
                if (response.isSuccessful) {
                    val taskList = response.body()!!
                    for (task in taskList) {
                        val foundTask = taskDao.findById(task.id!!)
                        if (foundTask != null) {
                            foundTask.update(task)
                            taskDao.update(foundTask)
                        } else {
                            taskDao.insert(
                                Task(
                                    null,
                                    task.id,
                                    task.name,
                                    task.description,
                                    task.assignedTo,
                                    task.dueDate,
                                    task.status.name
                                )
                            )
                        }
                    }
                } else {
                    Log.d("Developer", "Request Error: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
//                Log.e("Developer", "Error", e)
            }
        }
    }


    fun saveTask(taskDto: TaskPlannerDto) {
        viewModelScope.launch(dispatcher) {
            try {
                val response = taskService.saveTask(taskDto)
                if (response.isSuccessful) {
                    taskDao.insert(Task(response.body()!!))
                } else {
                    taskDao.insert(Task(taskDto))
                }
                saveTaskResult.postValue(response.isSuccessful)
            } catch (e: Exception) {
                taskDao.insert(Task(taskDto))
                saveTaskResult.postValue(false)
            }
        }
    }

    fun syncTasks() {
        viewModelScope.launch(dispatcher) {

            try {
                val notSyncTask = taskDao.findNotUploadedTasks()
                for (task in notSyncTask) {
                    val response = taskService.saveTask(TaskPlannerDto(task))
                    if (response.isSuccessful) {
                        task.id = response.body()!!.id
                        taskDao.update(task)
                    }
                }
            } catch (e: Exception) {
                Log.e("Developer", "Error", e)
            }
        }
    }
}
