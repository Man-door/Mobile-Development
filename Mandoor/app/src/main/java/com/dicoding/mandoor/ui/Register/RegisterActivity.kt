package com.dicoding.mandoor.ui.Register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.RegMandorRequest
import com.dicoding.mandoor.api.RegUserRequest
import com.dicoding.mandoor.databinding.ActivityRegisterBinding
import com.dicoding.mandoor.response.RegMandorResponse
import com.dicoding.mandoor.response.RegUserResponse
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the user type (user or mandor) passed from onboarding
        val userType = intent.getStringExtra("USER_TYPE")  // Expecting "user" or "mandor"

        binding.loginLink.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullName.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // Validate input fields
            if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (userType == "mandor") {
                    registerMandor(fullName, username, email, password)
                } else {
                    registerUser(fullName, username, email, password)
                }
            }
        }
    }

    private fun registerUser(fullName: String, username: String, email: String, password: String) {
        val request = RegUserRequest(fullName, username, email, password)

        // Use ApiConfig.instance to call the registerUser API
        ApiConfig.instance.registerUser(request).enqueue(object : Callback<RegUserResponse> {
            override fun onResponse(call: Call<RegUserResponse>, response: Response<RegUserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful: ${responseBody?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Move to LoginActivity
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterError", "Error: $errorBody")
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegUserResponse>, t: Throwable) {
                Log.e("RegisterError", "Network Error: ${t.message}")
                Toast.makeText(
                    this@RegisterActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun registerMandor(fullName: String, username: String, email: String, password: String) {
        val request = RegMandorRequest(fullName, username, email, password)

        // Use ApiConfig.instance to call the registerMandor API
        ApiConfig.instance.registerMandor(request).enqueue(object : Callback<RegMandorResponse> {
            override fun onResponse(call: Call<RegMandorResponse>, response: Response<RegMandorResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful: ${responseBody?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Move to LoginActivity
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterError", "Error: $errorBody")
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegMandorResponse>, t: Throwable) {
                Log.e("RegisterError", "Network Error: ${t.message}")
                Toast.makeText(
                    this@RegisterActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}


