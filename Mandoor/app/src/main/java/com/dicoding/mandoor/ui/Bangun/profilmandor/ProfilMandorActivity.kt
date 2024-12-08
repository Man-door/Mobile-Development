package com.dicoding.mandoor.ui.Bangun.profilmandor

import Mandor
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mandoor.databinding.ActivityProfilMandorBinding

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
            binding.numberproyek.text = (it.numberProyek ?: "N/A").toString()
            binding.jarakmandorprofil.text = it.jangkauan ?: "N/A"
            binding.deskripmandorprofil.text = it.layananLain ?: "N/A"
        }
    }

    // Tangani tombol back
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Kembali ke halaman sebelumnya
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

