package com.dicoding.mandoor.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mandoor.databinding.ItemUserActivityBinding
import com.dicoding.mandoor.ui.Bangun.rincian.RincianActivity

class BookingAdapter(private var bookingList: List<Booking>) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(val binding: ItemUserActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemUserActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        with(holder.binding) {
            ivcust.setImageResource(booking.customerImage)
            tvcust.text = booking.customerName
            tvbangun.text = booking.serviceType
            tvharga.text = booking.price
            statusText.text = booking.status

            // Listener untuk tombol Chat
            chatButton.setOnClickListener {
                val context = holder.itemView.context
                val phoneNumber = "628123456789" // Ganti dengan nomor WhatsApp tujuan
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://wa.me/$phoneNumber")
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "WhatsApp tidak terpasang.", Toast.LENGTH_SHORT).show()
                }
            }

            rincianButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, RincianActivity::class.java)
                intent.putExtra("booking", booking)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = bookingList.size

    fun updateData(newBookingList: List<Booking>) {
        bookingList = newBookingList
        notifyDataSetChanged()
    }
}
