package com.example.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskplanner.databinding.ActivityMainBinding
import com.example.taskplanner.service.RetrofitGenerator
import com.example.taskplanner.service.TaskService
import com.example.taskplanner.service.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = RetrofitGenerator.getInstance()
        val taskService = retrofit.create(TaskService::class.java)

        val user = UserInfo("santiago@mail.com","passw0rd")

        GlobalScope.launch(Dispatchers.IO) {
            val response = taskService.authTaskPlanner(user)
            if (response.isSuccessful){
                val response = response.body()
                Log.d("Developer", "Response:  $response")

            }else{
                //TODO implementar caso error
            }

        }

        GlobalScope.launch(Dispatchers.IO) {
            val response = taskService.getTaskList()
            if (response.isSuccessful){
                val tokenDto = response.body()
                Log.d("Developer", "Response:  $tokenDto")

            }
        }
    }
}