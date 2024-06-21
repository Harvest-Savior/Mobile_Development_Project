package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.DeteksiFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.HomeFragment
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment.RiwayatFragment

class DashboardAdapter(activity: FragmentActivity,private val tokenDeteksi: String?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> DeteksiFragment()
            2 -> RiwayatFragment.newInstance(tokenDeteksi ?: "")
            else -> Fragment()
        }
    }
}