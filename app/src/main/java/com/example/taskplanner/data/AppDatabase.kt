package com.example.taskplanner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.data.entity.Task



@Database(entities = [Task::class], version = 1)
@TypeConverters(MyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
