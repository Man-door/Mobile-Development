package com.dicoding.mandoor.ui.Bangun.listmandor

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.ListMandorAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityListMandorBinding
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.ui.Bangun.profilmandor.ProfilMandorActivity
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMandorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMandorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMandorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarlist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvlistMandor.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        getMandorData("Bearer $token")
    }

    private fun getMandorData(token: String) {
        ApiConfig.mainInstance.getMandor(token).enqueue(object :
            Callback<List<MandorResponseItem>> {
            override fun onResponse(call: Call<List<MandorResponseItem>>, response: Response<List<MandorResponseItem>>) {
                if (response.isSuccessful) {
                    val mandorItems = response.body()?.map { item ->
                        Mandor(
                            img = item.img,
                            fullName = item.fullName,
                            ratingUser = item.ratingUser,
                            numberProyek = item.numberProyek,
                            jangkauan = item.jangkauan,
                            layananLain = item.layananLain
                        )
                    } ?: emptyList()

                    val adapter = ListMandorAdapter(mandorItems) { selectedMandor ->
                        // Navigasi ke ProfilMandorActivity
                        val intent = Intent(this@ListMandorActivity, ProfilMandorActivity::class.java)
                        intent.putExtra("mandor", selectedMandor)
                        startActivity(intent)
                    }
                    binding.rvlistMandor.adapter = adapter
                } else {
                    Toast.makeText(this@ListMandorActivity, "Failed to load data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MandorResponseItem>>, t: Throwable) {
                Toast.makeText(this@ListMandorActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
