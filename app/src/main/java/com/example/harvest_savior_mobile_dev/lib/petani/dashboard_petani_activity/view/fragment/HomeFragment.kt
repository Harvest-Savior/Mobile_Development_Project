package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.FragmentHomeBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel.DashboardPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.setting_activity.activity.SettingPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.example.harvest_savior_mobile_dev.util.adapter.RekomendasiObatAdapter
import com.example.harvest_savior_mobile_dev.util.datastore


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentHomeBinding? =  null

    private lateinit var viewModel: DashboardPetaniViewModel
    private lateinit var repo: UserFarmerRepository
    private lateinit var pref : LoginPreference
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var rekomendasiObatAdapter: RekomendasiObatAdapter
    private val rekomendasiObatList = listOf(
        Obat("Fungisida", "Untuk mengendalikan penyakit, seperti NOPAT...", "Rp5.500"),
        Obat("Insektisida", "Untuk mengendalikan hama, seperti Pounce, B...", "Rp6.500")

    )

    private var token2 : String? = null
    private var namaUser : String? = null
    private var emailToko : String? = null

    private var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        rekomendasiObatAdapter = RekomendasiObatAdapter(rekomendasiObatList, requireActivity())
        recyclerView.adapter = rekomendasiObatAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiConfig.getApiService()
        pref = LoginPreference.getInstance(requireActivity().datastore)
        repo = UserFarmerRepository(apiService)
        val dashboardVMFactory = LoginViewModelFactory(repo,pref)
        viewModel = ViewModelProvider(this, dashboardVMFactory).get(DashboardPetaniViewModel::class.java)
        token2 = requireActivity().intent.getStringExtra("token")
        namaUser = requireActivity().intent.getStringExtra("namaToko")
        emailToko = requireActivity().intent.getStringExtra("email")


        viewModel.loginResultDat.observe(viewLifecycleOwner) {
            it.onSuccess { response ->
                url = response.url
                // Load the image using Glide
                if (!url.isNullOrEmpty()) {
                    Glide.with(requireActivity())
                        .load(url)
                        .circleCrop()
                        .placeholder(R.drawable.profile_2)
                        .into(binding.ivProfilePetani)
                }
            }.onFailure {

            }
        }

        viewModel.loginData(token2!!)

        val imgProfil2 = "http://34.50.79.94:8080/uploads/6064677217f5eb0f86d561957bf83c1b.jpg"

        binding.tvWelcomeHomePetani.text = "Selamat Datang, $namaUser"
        binding.ivProfilePetani.setOnClickListener {
            val intent = Intent(requireActivity(), SettingPetaniActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(),intent)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}