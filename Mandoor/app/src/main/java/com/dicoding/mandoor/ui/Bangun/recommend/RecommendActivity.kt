package com.dicoding.mandoor.ui.Bangun.recommend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Mandor
import com.dicoding.mandoor.adapter.RecommendAdapter
import com.dicoding.mandoor.databinding.ActivityRecommendBinding

class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rvMandors
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mandorList = listOf(
            Mandor(R.drawable.pak_vinsen, "Pak Vinsen", "4.5", "15 Proyek", "Jabodetabek", "Rp 250.000"),
            Mandor(R.drawable.pak_vinsen, "Pak Joko", "4.0", "12 Proyek", "Bandung", "Rp 300.000")
        )

        val adapter = RecommendAdapter(mandorList)
        recyclerView.adapter = adapter
    }
}

