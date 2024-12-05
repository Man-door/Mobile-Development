package com.dicoding.mandoor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mandoor.databinding.ItemRecommendBinding

class RecommendAdapter(private val mandorList: List<Mandor>) : RecyclerView.Adapter<RecommendAdapter.MandorViewHolder>() {

    class MandorViewHolder(private val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mandor: Mandor) {
            binding.mandorImage.setImageResource(mandor.imageRes)
            binding.mandorname.text = mandor.name
            binding.ratemandor.text = mandor.rating
            binding.totalproyek.text = mandor.totalProyek
            binding.jangkauanmandorrecommend.text = mandor.jangkauan
            binding.descmandor.text = mandor.deskripsi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandorViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MandorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MandorViewHolder, position: Int) {
        holder.bind(mandorList[position])
    }

    override fun getItemCount(): Int = mandorList.size
}

