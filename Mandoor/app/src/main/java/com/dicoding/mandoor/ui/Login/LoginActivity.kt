package com.dicoding.mandoor.ui.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.mandoor.MainActivity
import com.dicoding.mandoor.R
import com.dicoding.mandoor.databinding.ActivityLoginBinding
import com.dicoding.mandoor.ui.Register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userType = intent.getStringExtra("USER_TYPE")

        binding.registerLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
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

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userType == "user") {
                loginViewModel.loginUser(email, password)
            } else {
                Toast.makeText(this, "This account type is not supported", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
            } else {
            }
        })

        // Observe login success
        loginViewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Login Successful! Welcome.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        // Observe error message
        loginViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}



