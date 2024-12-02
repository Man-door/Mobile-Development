package com.dicoding.mandoor.ui.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.MainActivity
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.LogMandorRequest
import com.dicoding.mandoor.api.LogUserRequest
import com.dicoding.mandoor.databinding.ActivityLoginBinding
import com.dicoding.mandoor.response.LogMandorResponse
import com.dicoding.mandoor.response.LogUserResponse
import com.dicoding.mandoor.ui.Register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the user type (user or mandor) passed from onboarding
        val userType = intent.getStringExtra("USER_TYPE")  // Expecting "user" or "mandor"

        binding.registerLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (userType == "mandor") {
                    loginMandor(email, password)
                } else {
                    loginUser(email, password)
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val request = LogUserRequest(email, password)

        ApiConfig.instance.loginUser(request).enqueue(object : Callback<LogUserResponse> {
            override fun onResponse(call: Call<LogUserResponse>, response: Response<LogUserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Successful! Welcome.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Save token
                    val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user_token", responseBody?.token)
                    editor.apply()

                    // Move to MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LogUserResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loginMandor(email: String, password: String) {
        val request = LogMandorRequest(email, password)

        ApiConfig.instance.loginMandor(request).enqueue(object : Callback<LogMandorResponse> {
            override fun onResponse(call: Call<LogMandorResponse>, response: Response<LogMandorResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Successful! Welcome.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Save token
                    val sharedPreferences = getSharedPreferences("MandorSession", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("mandor_token", responseBody?.token)
                    editor.apply()

                    // Move to MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LogMandorResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}



