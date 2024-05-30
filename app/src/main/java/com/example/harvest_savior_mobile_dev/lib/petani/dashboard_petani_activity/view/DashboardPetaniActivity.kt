package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDashboardPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.DashboardAdapter
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.DeteksiFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.HomeFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.RiwayatFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel.DashboardPetaniViewModel

class DashboardPetaniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardPetaniBinding
    private lateinit var viewModel: DashboardPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.fragmentContainer
        val bottomNav = binding.bottomNavPetani

        viewPager.adapter = DashboardAdapter(this)
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
    }
}

