package com.dicoding.mandoor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.Booking
import com.dicoding.mandoor.adapter.BookingAdapter
import com.dicoding.mandoor.adapter.NewsAdapter
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.FragmentHomeBinding
import com.dicoding.mandoor.response.ArticlesItem
import com.dicoding.mandoor.response.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var bookingAdapter: BookingAdapter
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Set up RecyclerViews
        setupRecyclerViews()

        return root
    }

    private fun setupRecyclerViews() {
        // Initialize news adapter with empty list
        newsAdapter = NewsAdapter(emptyList())
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsRecyclerView.adapter = newsAdapter

        // Initialize booking adapter with empty list
        bookingAdapter = BookingAdapter(emptyList())
        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookingRecyclerView.adapter = bookingAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe live data from ViewModel
        homeViewModel.newsList.observe(viewLifecycleOwner, Observer { news ->
            if (!news.isNullOrEmpty()) {
                newsAdapter.updateData(news)
            } else {
                Toast.makeText(requireContext(), "No news available", Toast.LENGTH_SHORT).show()
            }
        })

        homeViewModel.bookingList.observe(viewLifecycleOwner, Observer { bookings ->
            if (!bookings.isNullOrEmpty()) {
                bookingAdapter.updateData(bookings)
            }
        })

        // Fetch data
        homeViewModel.fetchNews() // Fetch news
        homeViewModel.fetchBookings() // Optionally fetch bookings
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




