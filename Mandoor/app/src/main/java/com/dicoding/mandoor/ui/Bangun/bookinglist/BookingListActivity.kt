package com.dicoding.mandoor.ui.Bangun.bookinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.adapter.BookingAdapter
import com.dicoding.mandoor.databinding.ActivityBookingListBinding

class BookingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rvbookinglist
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bookingList = listOf(
            Booking(R.drawable.pak_vinsen, R.string.custname.toString(), R.string.total_proyek.toString(), R.string.jangkauan.toString(), R.string.descmandor.toString())
        )

        val adapter = BookingAdapter(bookingList)
        recyclerView.adapter = adapter
    }
}