package com.dicoding.mandoor.ui.Bangun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.mandoor.R
import com.dicoding.mandoor.databinding.FragmentBangunBinding

class BangunFragment : Fragment() {

    private var _binding: FragmentBangunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBangunBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.titleText.text = getString(R.string.bangun)

        binding.serviceName.text = getString(R.string.bangun)
        binding.serviceDescription.text = getString(R.string.DeskripsiBangun)

        binding.serviceName2.text = getString(R.string.renovasi)
        binding.serviceDescription2.text = getString(R.string.DeskripsiRenov)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
