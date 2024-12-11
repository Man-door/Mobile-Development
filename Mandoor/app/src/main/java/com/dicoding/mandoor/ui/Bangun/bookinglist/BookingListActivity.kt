package com.dicoding.mandoor.ui.Bangun.bookinglist

import Mandor
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.adapter.BookingListAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityBookingListBinding
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingListBinding
    private lateinit var adapter: BookingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedMandor: Mandor? = intent.getParcelableExtra("selectedMandor")

        // Setup RecyclerView
        adapter = BookingListAdapter(emptyList())
        binding.rvbookinglist.layoutManager = LinearLayoutManager(this)
        binding.rvbookinglist.adapter = adapter

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Fetch mandor data and filter based on selectedMandor
        getMandorData("Bearer $token", selectedMandor)
    }

    private fun getMandorData(token: String, selectedMandor: Mandor?) {
        ApiConfig.mainInstance.getMandor(token).enqueue(object : Callback<List<MandorResponseItem>> {
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

                    // Filter data mandor berdasarkan selectedMandor
                    val filteredMandorList = mandorItems.filter {
                        it.fullName == selectedMandor?.fullName &&
                                it.img == selectedMandor?.img
                    }.map {
                        Booking(
                            customerImage = R.drawable.image, // Placeholder image
                            customerName = it.fullName ?: "Unknown",
                            serviceType = it.layananLain ?: "Unknown",
                            price = "Rp. 0", // Tambahkan harga jika ada
                            status = "Pending"
                        )
                    }

                    adapter.updateData(filteredMandorList)
                } else {
                    Toast.makeText(this@BookingListActivity, "Failed to load data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MandorResponseItem>>, t: Throwable) {
                Toast.makeText(this@BookingListActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

