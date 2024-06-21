package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityDashboardPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.petani.camera_activity.view.CameraPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.camera_activity.view.CameraPetaniActivity.Companion.CAMERAX_RESULT
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.DashboardAdapter
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.DeteksiFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.HomeFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.RiwayatFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel.DashboardPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginJsonPetaniVM
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.datastore

class DashboardPetaniActivity : AppCompatActivity() {

    private var _binding: ActivityDashboardPetaniBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardPetaniViewModel
    private lateinit var viewModel2: LoginJsonPetaniVM
    private lateinit var repo: UserFarmerRepository
    private lateinit var pref : LoginPreference
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private var token2 : String? = null
    private var namaUser : String? = null
    private var emailToko : String? = null
    private var tokenDeteksi :String? = null

    private var url : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()
        pref = LoginPreference.getInstance(application.datastore)
        repo = UserFarmerRepository(apiService,apiService2)
        val dashboardVMFactory = LoginViewModelFactory(repo,pref)
        viewModel = ViewModelProvider(this, dashboardVMFactory).get(DashboardPetaniViewModel::class.java)

        val viewPager: ViewPager2 = binding.fragmentContainer
        val bottomNav = binding.bottomNavPetani

        viewModel.loginResultDat.observe(this) {
            it.onSuccess {response ->
                url = response.url
            }
        }



        viewPager.adapter = DashboardAdapter(this, tokenDeteksi)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewPager.currentItem = 0
                    true
                }

                R.id.navigation_deteksi -> {
                    viewPager.currentItem = 1
                    true
                }

                R.id.navigation_riwayat -> {
                    viewPager.currentItem = 2
                    true
                }

                else -> false
            }
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

        token2 = intent.getStringExtra("token")
        namaUser = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")
        viewModel.getTokenDeteksi().observe(this) { tokenD ->

            tokenDeteksi = tokenD
            Log.d(TAG, "Token Deteksi: $tokenDeteksi")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        private const val TOKEN_DETEKSI = "tokenDeteksi"

    }
}

