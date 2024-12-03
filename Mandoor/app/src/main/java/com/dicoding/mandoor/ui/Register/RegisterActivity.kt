package com.dicoding.mandoor.ui.Register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.mandoor.R
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.RegMandorRequest
import com.dicoding.mandoor.api.RegUserRequest
import com.dicoding.mandoor.databinding.ActivityRegisterBinding
import com.dicoding.mandoor.response.RegUserResponse
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userType = intent.getStringExtra("USER_TYPE")

        binding.loginLink.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.password.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.password.right - binding.password.compoundDrawables[2].bounds.width())) {
                    if (binding.password.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        binding.password.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock, 0, R.drawable.visibility, 0
                        )
                    } else {
                        binding.password.inputType =
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.lock, 0, R.drawable.visibility_off, 0
                        )
                    }
                    binding.password.setSelection(binding.password.text?.length ?: 0)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullName.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userType == "mandor") {
                registerViewModel.registerMandor(fullName, username, email, password)
            } else {
                registerViewModel.registerUser(fullName, username, email, password)
            }
        }

        registerViewModel.registerSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        registerViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        registerViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                // Show loading indicator
            } else {
                // Hide loading indicator
            }
        })
    }
}


