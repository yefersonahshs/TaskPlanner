package com.example.taskplanner.app.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.R
import com.example.taskplanner.data.entity.Task


class TasksAdapter(private val taskItemClickListener: TaskItemClickListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {


    private var taskList: List<Task> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val assignedTo: TextView = view.findViewById(R.id.assignedTo)
        val status: TextView = view.findViewById(R.id.status)
        val notSyncIcon: ImageView = view.findViewById(R.id.not_sync_icon)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.task_item_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.name.text = task.name
        holder.description.text = task.description
        holder.assignedTo.text = task.assignedTo
        holder.status.text = task.status
        if (task.id == null) {
            holder.notSyncIcon.visibility = View.VISIBLE
        } else {
            holder.notSyncIcon.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            taskItemClickListener.onTaskClicked(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateTasksList(list: List<Task>) {
        this.taskList = list
        notifyItemRangeChanged(0, list.size)
    }


}