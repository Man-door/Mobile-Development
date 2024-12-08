package com.dicoding.mandoor.adapter

import Mandor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mandoor.databinding.ItemRecommendBinding

class RecommendAdapter(
    private val mandorList: List<Mandor>,
    private val onMandorClick: (Mandor) -> Unit
) : RecyclerView.Adapter<RecommendAdapter.MandorViewHolder>() {

    class MandorViewHolder(private val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mandor: Mandor, onMandorClick: (Mandor) -> Unit) {
            // Gunakan Glide untuk memuat gambar dari URL
            Glide.with(binding.mandorImage.context)
                .load(mandor.img)
                .into(binding.mandorImage)

            binding.mandorname.text = mandor.fullName
            binding.ratemandor.text = mandor.ratingUser.toString()

            itemView.setOnClickListener {
                onMandorClick(mandor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandorViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MandorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MandorViewHolder, position: Int) {
        holder.bind(mandorList[position], onMandorClick)
    }

    override fun getItemCount(): Int = mandorList.size
}

