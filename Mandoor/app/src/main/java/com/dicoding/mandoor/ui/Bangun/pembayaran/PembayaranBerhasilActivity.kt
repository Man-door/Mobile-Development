package com.dicoding.mandoor.ui.Bangun.pembayaran

import Mandor
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mandoor.databinding.ActivityPembayaranBerhasilBinding
import com.dicoding.mandoor.ui.Bangun.rincian.RincianActivity

class PembayaranBerhasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBerhasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBerhasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button untuk mengarah ke Activity Rincian
        binding.btnPaymentDetails.setOnClickListener {
            val intent = Intent(this, RincianActivity::class.java)
            val mandor = intent.getParcelableExtra<Mandor>("mandor") // Jika data mandor ada
            intent.putExtra("mandor", mandor)
            startActivity(intent)
            finish()
        }
    }
}
