package com.example.taskplanner.app.task.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taskplanner.R
import com.example.taskplanner.app.task.viewmodel.TaskViewModel
import com.example.taskplanner.databinding.FragmentTaskFormBinding
import com.example.taskplanner.service.dto.StatusEnum
import com.example.taskplanner.service.dto.TaskPlannerDto
import java.util.*


class TaskFormFragment : Fragment(), TimePickerListener, AdapterView.OnItemSelectedListener {

    private val viewModel: TaskViewModel by activityViewModels()

    private var _binding: FragmentTaskFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskFormBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTaskData()
        addClickListeners()
        configureTaskStatusSpinner()
        addLiveDataObservers()
    }

    private fun addLiveDataObservers() {
        viewModel.selectedDateLiveData.observe(viewLifecycleOwner, { selectedDate ->
            binding.dueDate.text = selectedDate.toString()
        })
        viewModel.saveTaskResult.observe(viewLifecycleOwner, {
            resetFormFields()

        })
    }

    private fun resetFormFields() {
        binding.name.text = null
        binding.description.text = null
        binding.assignedTo.text = null
        binding.dueDate.text = null
    }

    private fun configureTaskStatusSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.task_status_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.taskStatusSpinner.adapter = adapter
            binding.taskStatusSpinner.onItemSelectedListener = this
        }
    }

    private fun addClickListeners() {
        binding.dueDate.setOnClickListener {
            DatePickerFragment(this).show(parentFragmentManager, "DatePicker")
        }
        binding.buttonSave.setOnClickListener {
            if (validTaskFormFields()) {
                viewModel.saveTask(
                    TaskPlannerDto(
                        null,
                        binding.name.text.toString(),
                        binding.description.text.toString(),
                        StatusEnum.valueOf(binding.taskStatusSpinner.selectedItem as String),
                        binding.assignedTo.text.toString(),
                        viewModel.selectedDateLiveData.value!!
                    )
                )
            }
        }
    }

    private fun validTaskFormFields(): Boolean {
        when {
            binding.name.text.isEmpty() -> {
                binding.name.error = getString(R.string.empty_field_error)
                return false
            }
            binding.description.text.isEmpty() -> {
                binding.name.error = null
                binding.description.error = getString(R.string.empty_field_error)
                return false
            }
            binding.assignedTo.text.isEmpty() -> {
                binding.name.error = null
                binding.description.error = null
                binding.assignedTo.error = getString(R.string.empty_field_error)
                return false
            }
            binding.dueDate.text.isEmpty() -> {
                binding.name.error = null
                binding.description.error = null
                binding.assignedTo.error = null
                binding.dueDate.error = getString(R.string.empty_field_error)
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun loadTaskData() {
        val selectedTask = viewModel.selectedTask
        if (selectedTask != null) {
            binding.name.setText(selectedTask.name)
            binding.description.setText(selectedTask.description)
            binding.assignedTo.setText(selectedTask.assignedTo)
            binding.dueDate.text = selectedTask.dueDate.toString()
        } else {
            binding.name.text = null
            binding.description.text = null
            binding.assignedTo.text = null
            binding.dueDate.text = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTimeSelected(date: Date) {
        viewModel.selectedDateLiveData.postValue(date)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val itemAtPosition: String = parent?.getItemAtPosition(pos) as String
        Log.d("Developer", "itemAtPosition: $itemAtPosition")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}