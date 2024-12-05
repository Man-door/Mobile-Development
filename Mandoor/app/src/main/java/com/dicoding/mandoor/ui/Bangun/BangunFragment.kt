package com.dicoding.mandoor.ui.Bangun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.LoadingAdapter
import com.dicoding.mandoor.databinding.FragmentBangunBinding
import com.dicoding.mandoor.ui.Bangun.survey.SurveyBangunActivity
import com.dicoding.mandoor.ui.Bangun.survey.SurveyRenovActivity

class BangunFragment : Fragment() {

    private var _binding: FragmentBangunBinding? = null
    private val binding get() = _binding!!

    private val bangunViewModel: BangunViewModel by activityViewModels()
    private lateinit var loadingAdapter: LoadingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBangunBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadingAdapter = LoadingAdapter(requireContext())

        bangunViewModel.setServiceData()

        bangunViewModel.serviceName.observe(viewLifecycleOwner) {
            binding.serviceName.text = it
        }

        bangunViewModel.serviceDescription.observe(viewLifecycleOwner) {
            binding.serviceDescription.text = it
        }

        bangunViewModel.serviceName2.observe(viewLifecycleOwner) {
            binding.serviceName2.text = it
        }

        bangunViewModel.serviceDescription2.observe(viewLifecycleOwner) {
            binding.serviceDescription2.text = it
        }

        binding.root.findViewById<CardView>(R.id.cvbangun).setOnClickListener {
            loadingAdapter.showLoading()
            val intent = Intent(activity, SurveyBangunActivity::class.java)
            intent.putExtra("service_type", "bangun")
            startActivity(intent)
            loadingAdapter.dismissLoading()
        }

        binding.root.findViewById<CardView>(R.id.cvrenov).setOnClickListener {
            loadingAdapter.showLoading()
            val intent = Intent(activity, SurveyRenovActivity::class.java)
            intent.putExtra("service_type", "renovasi")
            startActivity(intent)
            loadingAdapter.dismissLoading()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

