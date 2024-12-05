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
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val mandorList = listOf(
            Mandor(R.drawable.pak_vinsen, R.string.mandorname.toString(),
                R.string.total_proyek.toString(),
                R.drawable.star.toString(), R.string.rating.toString(), R.string.descmandor.toString()
            ),
            Mandor(R.drawable.pak_vinsen, R.string.mandorname.toString(),
                R.string.total_proyek.toString(),
                R.drawable.star.toString(), R.string.rating.toString(), R.string.descmandor.toString())
        )

        val adapter = RecommendAdapter(mandorList)
        recyclerView.adapter = adapter
    }
}

