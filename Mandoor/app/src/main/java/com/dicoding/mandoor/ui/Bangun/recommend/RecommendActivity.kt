package com.dicoding.mandoor.ui.Bangun.recommend

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.adapter.RecommendAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityRecommendBinding
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.ui.Bangun.profilmandor.ProfilMandorActivity
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarrecommend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = binding.rvMandors
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val apiService = ApiConfig.mainInstance
        apiService.getMandor("Bearer $token").enqueue(object : Callback<List<MandorResponseItem>> {
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
                    }?.take(5) ?: emptyList()

                    val adapter = RecommendAdapter(mandorItems) { selectedMandor ->
                        val intent = Intent(this@RecommendActivity, ProfilMandorActivity::class.java)
                        intent.putExtra("mandor", selectedMandor)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@RecommendActivity, "Failed to load data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MandorResponseItem>>, t: Throwable) {
                Toast.makeText(this@RecommendActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
