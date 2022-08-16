package com.example.taskplanner.app.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.taskplanner.R
import com.example.taskplanner.app.activity.TasksActivity
import com.example.taskplanner.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addLiveDataObservers()
        addClickListener()
    }

    private fun addLiveDataObservers() {
        val model: LoginViewModel by viewModels()
        model.labelLiveData.observe(this, {
            binding.label.text = it
        })
        model.requestResultLiveData.observe(this, { isSuccessful ->
            if (isSuccessful) {
                binding.progressBar.visibility = View.GONE
                startActivity(Intent(baseContext, TasksActivity::class.java))
                finish()
            } else {
                binding.loginButton.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun addClickListener() {
        binding.loginButton.setOnClickListener { view ->
            if (isLoginFormValid()) {
                view.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                val model: LoginViewModel by viewModels()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                model.labelLiveData.postValue(email)
                model.auth(email, password)
            }
        }
    }

    private fun isLoginFormValid(): Boolean {
        val emailText = binding.emailEditText.text
        if (emailText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            binding.emailEditText.error = null
        } else {
            binding.emailEditText.error = getString(R.string.invalid_email)
            return false
        }
        return if (binding.passwordEditText.text.isNotEmpty()) {
            binding.passwordEditText.error = null
            true
        } else {
            binding.passwordEditText.error = getString(R.string.empty_password_field)
            false
        }
    }


}