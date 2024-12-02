package com.dicoding.mandoor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mandoor.databinding.ItemUserActivityBinding

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
            tvdate.text = booking.date
        }
    }

    override fun getItemCount(): Int = bookingList.size

    fun updateData(newBookingList: List<Booking>) {
        bookingList = newBookingList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}

