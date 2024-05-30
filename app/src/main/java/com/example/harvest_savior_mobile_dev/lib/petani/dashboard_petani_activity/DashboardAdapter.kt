package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.DeteksiFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.HomeFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.RiwayatFragment

class DashboardAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        HomeFragment(),
        DeteksiFragment(),
        RiwayatFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}