package com.dicoding.mandoor.adapter

import Mandor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mandoor.R

class ListMandorAdapter(
    private val mandorList: List<Mandor>,
    private val onItemClick: (Mandor) -> Unit
) : RecyclerView.Adapter<ListMandorAdapter.MandorViewHolder>() {

    inner class MandorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.namamandor)
        private val ratingTextView: TextView = view.findViewById(R.id.numratelist)
        private val totalProyekTextView: TextView = view.findViewById(R.id.numberproyeklist)
        private val jangkauanTextView: TextView = view.findViewById(R.id.jangkauanmandorlist)
        private val descriptionTextView: TextView = view.findViewById(R.id.deskripmandorlist)
        private val imageView: ImageView = view.findViewById(R.id.itemImagelist)

        fun bind(mandor: Mandor) {
            nameTextView.text = mandor.fullName
            ratingTextView.text = mandor.ratingUser?.toString() ?: "-"
            totalProyekTextView.text = mandor.numberProyek?.toString() ?: "-"
            jangkauanTextView.text = mandor.jangkauan ?: "-"
            descriptionTextView.text = mandor.layananLain ?: "-"

            Glide.with(itemView.context)
                .load(mandor.img)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .into(imageView)

            itemView.setOnClickListener { onItemClick(mandor) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listmandor, parent, false)
        return MandorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MandorViewHolder, position: Int) {
        holder.bind(mandorList[position])
    }

    override fun getItemCount(): Int = mandorList.size
}
