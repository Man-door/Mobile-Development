package com.dicoding.mandoor.ui.Register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.LoadingAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.RegUserRequest
import com.dicoding.mandoor.databinding.ActivityRegisterBinding
import com.dicoding.mandoor.response.RegUserResponse
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loadingAdapter: LoadingAdapter
    private lateinit var registerViewModel: RegisterViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingAdapter = LoadingAdapter(this)

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

            if (fullName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(fullName, username, email, password)
            } else {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(fullName: String, username: String, email: String, password: String) {
        loadingAdapter.showLoading()

        val request = RegUserRequest(fullName, username, email, password)
        val apiService = ApiConfig.mainInstance
        apiService.registerUser(request).enqueue(object : Callback<RegUserResponse> {
            override fun onResponse(call: Call<RegUserResponse>, response: Response<RegUserResponse>) {
                loadingAdapter.dismissLoading()
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)

                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registrasi gagal. Coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegUserResponse>, t: Throwable) {
                loadingAdapter.dismissLoading()
                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

