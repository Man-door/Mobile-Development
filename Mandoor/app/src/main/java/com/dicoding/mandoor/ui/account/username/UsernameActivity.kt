package com.dicoding.mandoor.ui.account.username

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
import com.dicoding.mandoor.databinding.ActivityNamaBinding
import com.dicoding.mandoor.databinding.ActivityUsernameBinding
import com.dicoding.mandoor.response.AccountPUTResponse
import com.dicoding.mandoor.response.User
import com.dicoding.mandoor.ui.account.AccountFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsernameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsernameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan token dari SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        // Jika token tidak ada, tampilkan pesan dan keluar
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarusernama)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Menampilkan username yang sudah ada
        val currentUsername = intent.getStringExtra("currentUsername") ?: ""
        binding.edUbahUsername.setText(currentUsername)

        // Set listener untuk tombol simpan
        binding.btnsimpanusername.setOnClickListener {
            val newUsername = binding.edUbahUsername.text.toString().trim()

            if (newUsername.isEmpty()) {
                Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mengirim request PUT untuk memperbarui username
            updateUserUsername(token, newUsername)
        }
    }

    private fun updateUserUsername(token: String, newUsername: String) {
        val requestBody = User(
            username = newUsername
        )

        ApiConfig.mainInstance.updateAccount("Bearer $token", requestBody)
            .enqueue(object : Callback<AccountPUTResponse> {
                override fun onResponse(call: Call<AccountPUTResponse>, response: Response<AccountPUTResponse>) {
                    if (response.isSuccessful) {
                        val updatedUser = response.body()?.user
                        if (updatedUser != null) {
                            // Menyimpan data yang diperbarui
                            saveUpdatedUser(updatedUser)

                            // Kembali ke AccountFragment dan memperbarui UI
                            val intent = Intent(this@UsernameActivity, AccountFragment::class.java)
                            intent.putExtra("updatedUsername", updatedUser.username)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@UsernameActivity, "Gagal memperbarui username: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AccountPUTResponse>, t: Throwable) {
                    Toast.makeText(this@UsernameActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun saveUpdatedUser(user: User) {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_username", user.username)
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

