package com.dicoding.mandoor.ui.account.email

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.mandoor.R
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityEmailBinding
import com.dicoding.mandoor.response.AccountPUTResponse
import com.dicoding.mandoor.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbaremail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.btnsimpanemail.setOnClickListener {
            val newEmail = binding.edUbahEmail.text.toString().trim()

            if (newEmail.isEmpty()) {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserEmail(token, newEmail)
        }
    }

    private fun updateUserEmail(token: String, newEmail: String) {
        val requestBody = User(email = newEmail)

        ApiConfig.mainInstance.updateAccount("Bearer $token", requestBody)
            .enqueue(object : Callback<AccountPUTResponse> {
                override fun onResponse(call: Call<AccountPUTResponse>, response: Response<AccountPUTResponse>) {
                    if (response.isSuccessful) {
                        val updatedUser = response.body()?.user
                        if (updatedUser != null) {
                            saveUpdatedEmail(updatedUser)

                            val resultIntent = Intent()
                            resultIntent.putExtra("updatedEmail", updatedUser.email)
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@EmailActivity, "Gagal memperbarui email: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AccountPUTResponse>, t: Throwable) {
                    Toast.makeText(this@EmailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun saveUpdatedEmail(user: User) {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_email", user.email)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
