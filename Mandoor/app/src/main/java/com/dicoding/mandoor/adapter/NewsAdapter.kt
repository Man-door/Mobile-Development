package com.dicoding.mandoor.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mandoor.databinding.ItemNewsBinding
import com.dicoding.mandoor.response.ArticlesItem

class NewsAdapter(private var newsList: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        with(holder.binding) {
            newsTitle.text = news.title
            newsDescription.text = news.description
            // Use Glide to load the image
            Glide.with(holder.itemView.context)
                .load(news.urlToImage)
                .into(newsImage)

            // Handle the click to open the news link or video
            root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newList: List<ArticlesItem?>?) {
        newsList = newList as List<ArticlesItem>
        notifyDataSetChanged()
    }
}


