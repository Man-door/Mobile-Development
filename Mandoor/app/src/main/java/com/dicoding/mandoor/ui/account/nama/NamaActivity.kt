package com.dicoding.mandoor.ui.account.nama

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.R
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityNamaBinding
import com.dicoding.mandoor.response.AccountPUTResponse
import com.dicoding.mandoor.response.User
import com.dicoding.mandoor.ui.account.AccountFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NamaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNamaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarnama)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val currentUsername = intent.getStringExtra("currentUsername") ?: ""
        binding.edUbahFullnama.setText(currentUsername)

        binding.btnsimpanfullname.setOnClickListener {
            val newFullName = binding.edUbahFullnama.text.toString().trim()

            if (newFullName.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserFullName(token, newFullName)
        }
    }

    private fun updateUserFullName(token: String, newFullName: String) {
        val requestBody = User(
            fullName = newFullName,
        )

        ApiConfig.mainInstance.updateAccount("Bearer $token", requestBody)
            .enqueue(object : Callback<AccountPUTResponse> {
                override fun onResponse(call: Call<AccountPUTResponse>, response: Response<AccountPUTResponse>) {
                    if (response.isSuccessful) {
                        val updatedUser = response.body()?.user
                        if (updatedUser != null) {
                            saveUpdatedUser(updatedUser)

                            val intent = Intent(this@NamaActivity, AccountFragment::class.java)
                            intent.putExtra("updatedFullName", updatedUser.fullName)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@NamaActivity, "Gagal memperbarui nama: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AccountPUTResponse>, t: Throwable) {
                    Toast.makeText(this@NamaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun saveUpdatedUser(user: User) {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_full_name", user.fullName)
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


