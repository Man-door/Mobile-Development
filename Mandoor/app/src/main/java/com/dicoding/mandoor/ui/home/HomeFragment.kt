package com.dicoding.mandoor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandoor.adapter.BookingAdapter
import com.dicoding.mandoor.adapter.NewsAdapter
import com.dicoding.mandoor.databinding.FragmentHomeBinding

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

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupRecyclerViews()

        return root
    }

    private fun setupRecyclerViews() {
        newsAdapter = NewsAdapter(emptyList())
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsRecyclerView.adapter = newsAdapter

        bookingAdapter = BookingAdapter(emptyList())
        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookingRecyclerView.adapter = bookingAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        homeViewModel.fetchNews()
        homeViewModel.fetchBookings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




