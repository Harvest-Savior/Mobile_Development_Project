package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.PredictionsItem
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.FragmentHomeBinding
import com.example.harvest_savior_mobile_dev.databinding.FragmentRiwayatBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.DashboardPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel.DashboardPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.example.harvest_savior_mobile_dev.util.adapter.RiwayatObatAdapter
import com.example.harvest_savior_mobile_dev.util.datastore


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RiwayatFragment : Fragment() {
    private var tokenDeteksi: String? = null

    private var _binding : FragmentRiwayatBinding? = null
    private lateinit var viewModel: DashboardPetaniViewModel
    private lateinit var repo: UserFarmerRepository
    private lateinit var pref : LoginPreference
    private val binding get() = _binding!!

    private lateinit var obatAdapter: RiwayatObatAdapter
    private var predictions: List<PredictionsItem?>? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tokenDeteksi = it.getString(ARG_TOKEN_DETEKSI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(layoutInflater,container,false)
        recyclerView = binding.rvRiwayat
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        obatAdapter = RiwayatObatAdapter(emptyList(),requireContext())
        recyclerView.adapter = obatAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()
        pref = LoginPreference.getInstance(requireActivity().datastore)
        repo = UserFarmerRepository(apiService,apiService2)
        val riwayatVMFactory = LoginViewModelFactory(repo,pref)
        viewModel = ViewModelProvider(this, riwayatVMFactory).get(DashboardPetaniViewModel::class.java)
        viewModel.getTokenDeteksi().observe(requireActivity()) { tokenD ->
            tokenDeteksi = tokenD
            Log.d(TAG, "Token Deteksi: $tokenDeteksi")
        }

        viewModel.historyResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                setupRecyclerView()
                predictions = response.predictions
            }.onFailure {

            }
        }

        tokenDeteksi?.let {
            viewModel.getHistory(it)
            Log.d(TAG,"nilai token deteksi : $it")
        }

        viewModel.loading.observe(requireActivity()){
            showLoading(it)
        }
    }

    private fun setupRecyclerView() {
        obatAdapter = RiwayatObatAdapter(emptyList(),requireActivity())
        binding.rvRiwayat.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = obatAdapter
        }
    }

    companion object {
        private const val ARG_TOKEN_DETEKSI = "tokenDeteksi"
        @JvmStatic
        fun newInstance(tokenDeteksi: String) =
            RiwayatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOKEN_DETEKSI, tokenDeteksi)
                }
            }

        private const val TAG = "RiwayatFragment"
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressRiwayat.visibility = View.VISIBLE
        } else {
            binding.progressRiwayat.visibility = View.GONE
        }
    }
}