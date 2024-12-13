package com.dicoding.mandoor.ui.Bangun.pembayaran

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mandoor.R
import com.dicoding.mandoor.databinding.ActivityPembayaranBinding

class PembayaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, PembayaranBerhasilActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.toolbarpembayaran)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mandor = intent.getParcelableExtra<Mandor>("mandor")

        mandor?.let {
            binding.tvName.text = it.fullName
            binding.tvTotalProjects.text = "Total Proyek: ${it.numberProyek ?: "N/A"}"
            binding.tvLocation.text = it.jangkauan ?: "N/A"
            binding.tvDescription.text = it.layananLain ?: "N/A"
            binding.tvRating.text = it.ratingUser?.toString() ?: "N/A"

            Glide.with(this)
                .load(it.img)
                .into(binding.ivProfilePicture)
        }

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, PembayaranBerhasilActivity::class.java)
            intent.putExtra("mandor", mandor)
            startActivity(intent)
            finish()
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
