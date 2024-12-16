package com.dicoding.mandoor.ui.Bangun.bookinglist

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.adapter.BookingListAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.ActivityBookingListBinding
import com.dicoding.mandoor.response.MandorResponseItem
import com.dicoding.mandoor.ui.Bangun.pembayaran.PembayaranActivity
import com.dicoding.mandoor.ui.Login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingListBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarbookinglist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val mandor = intent.getParcelableExtra<Mandor>("mandor")

        mandor?.let {
            binding.namamandorbook.text = it.fullName
            binding.numratebook.text = it.ratingUser?.toString() ?: "N/A"
            binding.numberproyekbook.text = it.numberProyek?.toString() ?: "0"
            binding.jangkauanmandorbook.text = it.jangkauan ?: "N/A"
            binding.deskripmandor.text = it.layananLain ?: "N/A"

            Glide.with(this)
                .load(it.img)
                .into(binding.itemImage)
        }

        binding.btnpembayaran.setOnClickListener {
            val intent = Intent(this, PembayaranActivity::class.java)
            intent.putExtra("mandor", mandor)
            startActivity(intent)
        }
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
