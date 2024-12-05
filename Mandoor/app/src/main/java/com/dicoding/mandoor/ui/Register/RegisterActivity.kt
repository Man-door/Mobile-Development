package com.dicoding.mandoor.ui.Register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.LoadingAdapter
import com.dicoding.mandoor.databinding.ActivityRegisterBinding
import com.dicoding.mandoor.ui.Login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingAdapter = LoadingAdapter(this)

        val userType = intent.getStringExtra("USER_TYPE")

        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("USER_TYPE", "user")
        startActivity(intent)

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

            if (userType == "user") {
                registerViewModel.registerUser(fullName, username, email, password)
            } else {
            }
        }

        registerViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                loadingAdapter.showLoading()
            } else {
                loadingAdapter.dismissLoading()
            }
        })

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
    }
}



