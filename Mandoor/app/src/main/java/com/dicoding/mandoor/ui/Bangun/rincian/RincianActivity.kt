package com.dicoding.mandoor.ui.Bangun.rincian

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.mandoor.MainActivity
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.databinding.ActivityRincianBinding
import com.dicoding.mandoor.ui.home.HomeFragment

class RincianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRincianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRincianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarrincian)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mandor = intent.getParcelableExtra<Mandor>("mandor")

        val booking = intent.getParcelableExtra<Booking>("booking")

        mandor?.let {
            binding.namamandorrincian.text = it.fullName
            binding.numraterincian.text = it.ratingUser?.toString() ?: "N/A"
            binding.numberproyekrincian.text = it.numberProyek?.toString() ?: "0"
            binding.jarakmandorrincian.text = it.jangkauan ?: "N/A"
            binding.deskripmandorrincian.text = it.layananLain ?: "N/A"

            Glide.with(this)
                .load(it.img)
                .into(binding.ivmandorrincian)
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
