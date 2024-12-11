package com.dicoding.mandoor.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mandoor.R
import com.dicoding.mandoor.databinding.ItemBookingListBinding

class BookingListAdapter(private var bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingListAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(val binding: ItemBookingListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        Log.d("BookingListAdapter", "Binding data: $booking")

        with(holder.binding) {
            Glide.with(root.context)
                .load(booking.customerImage)
                .placeholder(R.drawable.image)
                .into(itemImage)

            namamandorbook.text = booking.customerName
            rateimagebook.setImageResource(R.drawable.star)
            numratebook.text = booking.serviceType
            totproyekbook.text = booking.price
            statusText.text = booking.status
        }
    }

    override fun getItemCount(): Int = bookingList.size

    fun updateData(newBookingList: List<Booking>) {
        bookingList = newBookingList
        notifyDataSetChanged()
    }
}
