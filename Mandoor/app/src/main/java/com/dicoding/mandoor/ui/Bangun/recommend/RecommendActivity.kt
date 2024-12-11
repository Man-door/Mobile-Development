package com.dicoding.mandoor.ui.Bangun.recommend

import Mandor
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.RecommendAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityRecommendBinding
import com.dicoding.mandoor.response.SurveyGETResponse
import com.dicoding.mandoor.response.SurveyGETResponseItem
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.ui.Bangun.listmandor.ListMandorActivity
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

        binding.seeAll.setOnClickListener {
            val intent = Intent(this, ListMandorActivity::class.java)
            startActivity(intent)
        }

        binding.rvMandors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        Log.d("RecommendActivity", "Token ditemukan: $token")

        // Fetch both mandor data and survey data
        getMandorData("Bearer $token")
        getSurveyData("Bearer $token")
    }

    private fun getMandorData(token: String) {
        val apiService = ApiConfig.mainInstance
        apiService.getMandor(token).enqueue(object : Callback<List<MandorResponseItem>> {
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
                    binding.rvMandors.adapter = adapter
                } else {
                    Toast.makeText(this@RecommendActivity, "Failed to load data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MandorResponseItem>>, t: Throwable) {
                Toast.makeText(this@RecommendActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getSurveyData(token: String) {
        val apiService = ApiConfig.mainInstance
        apiService.getSurvey(token).enqueue(object : Callback<SurveyGETResponse> {
            override fun onResponse(call: Call<SurveyGETResponse>, response: Response<SurveyGETResponse>) {
                if (response.isSuccessful) {
                    val surveyItems = response.body()?.surveyGETResponse?.filterNotNull()
                    if (!surveyItems.isNullOrEmpty()) {
                        val firstSurvey = surveyItems[0] // Mengambil item pertama
                        displaySurveyData(firstSurvey)
                    } else {
                        Toast.makeText(this@RecommendActivity, "No survey data found.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("SurveyError", "Error Code: ${response.code()}")
                    Log.e("SurveyError", "Error Body: ${response.errorBody()?.string()}")
                    Log.e("RecommendActivity", "Failed to load surveys: ${response.code()}, ${response.errorBody()?.string()}")
                    Toast.makeText(this@RecommendActivity, "Failed to load surveys: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SurveyGETResponse>, t: Throwable) {
                Log.e("RecommendActivity", "Error fetching surveys: ${t.message}")
                Toast.makeText(this@RecommendActivity, "Error fetching surveys: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun displaySurveyData(survey: SurveyGETResponseItem) {
        val tvRangeHargaRec = findViewById<TextView>(R.id.tv_rangeharga_rec)
        val tvCustomerDescription = findViewById<TextView>(R.id.customerDescription)
        val tvAlamatRec = findViewById<TextView>(R.id.alamat_rec)
        val tvTanggalRec = findViewById<TextView>(R.id.tanggal_rec)
        val ivFotoRec = findViewById<ImageView>(R.id.foto_rec)

        tvRangeHargaRec.text = survey.budget ?: "N/A"
        tvCustomerDescription.text = survey.deskripsi ?: "N/A"
        tvAlamatRec.text = survey.alamat ?: "N/A"
        tvTanggalRec.text = survey.tanggal ?: "N/A"

        // Load image using Glide (atau library lainnya)
        Glide.with(this)
            .load(survey.foto) // URL gambar
            .placeholder(R.drawable.image) // Gambar placeholder
            .error(R.drawable.image) // Gambar jika URL gagal
            .into(ivFotoRec)
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
