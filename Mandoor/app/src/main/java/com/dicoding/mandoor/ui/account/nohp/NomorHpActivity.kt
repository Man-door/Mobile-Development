package com.dicoding.mandoor.ui.account.nohp

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
import com.dicoding.mandoor.databinding.ActivityNomorHpBinding
import com.dicoding.mandoor.databinding.ActivityUsernameBinding
import com.dicoding.mandoor.response.AccountPUTResponse
import com.dicoding.mandoor.response.User
import com.dicoding.mandoor.ui.account.AccountFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NomorHpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNomorHpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNomorHpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarnohp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val currentPhoneNumber = intent.getStringExtra("currentPhoneNumber") ?: ""
        binding.edUbahNohp.setText(currentPhoneNumber)

        binding.btnsimpanohp.setOnClickListener {
            val newPhoneNumber = binding.edUbahNohp.text.toString().trim()

            if (newPhoneNumber.isEmpty()) {
                Toast.makeText(this, "Nomor HP tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserPhoneNumber(token, newPhoneNumber)
        }
    }

    private fun updateUserPhoneNumber(token: String, newPhoneNumber: String) {
        val requestBody = User(phoneNumber = newPhoneNumber)

        ApiConfig.mainInstance.updateAccount("Bearer $token", requestBody)
            .enqueue(object : Callback<AccountPUTResponse> {
                override fun onResponse(call: Call<AccountPUTResponse>, response: Response<AccountPUTResponse>) {
                    if (response.isSuccessful) {
                        val updatedUser = response.body()?.user
                        if (updatedUser != null) {
                            saveUpdatedPhoneNumber(updatedUser)

                            val resultIntent = Intent()
                            resultIntent.putExtra("updatedPhoneNumber", updatedUser.phoneNumber)
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@NomorHpActivity, "Gagal memperbarui nomor HP: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AccountPUTResponse>, t: Throwable) {
                    Toast.makeText(this@NomorHpActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun saveUpdatedPhoneNumber(user: User) {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_phone_number", user.phoneNumber)
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
