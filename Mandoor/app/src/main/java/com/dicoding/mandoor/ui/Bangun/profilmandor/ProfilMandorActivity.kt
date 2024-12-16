package com.dicoding.mandoor.ui.Bangun.profilmandor

import Mandor
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mandoor.databinding.ActivityProfilMandorBinding
import com.dicoding.mandoor.ui.Bangun.bookinglist.BookingListActivity

class ProfilMandorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilMandorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilMandorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarprofilmandor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mandor = intent.getParcelableExtra<Mandor>("mandor")

        mandor?.let {
            Glide.with(this)
                .load(it.img)
                .into(binding.ivmandor)

            binding.numrate.text = it.ratingUser?.toString() ?: "N/A"
            binding.namamandorprofil.text = it.fullName ?: "N/A"
            binding.numberproyek.text = it.numberProyek?.toString() ?: "N/A"
            binding.jarakmandorprofil.text = it.jangkauan ?: "N/A"
            binding.deskripmandorprofil.text = it.layananLain ?: "N/A"
        }

        // Listener untuk tombol "Book Now"
        binding.btnsimpanbooknow.setOnClickListener {
            val intent = Intent(this, BookingListActivity::class.java)
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


