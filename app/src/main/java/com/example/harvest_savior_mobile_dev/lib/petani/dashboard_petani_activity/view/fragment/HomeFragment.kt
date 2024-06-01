package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.util.adapter.RekomendasiObatAdapter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var rekomendasiObatAdapter: RekomendasiObatAdapter
    private val rekomendasiObatList = listOf(
        Obat("Fungisida", "Untuk mengendalikan penyakit, seperti NOPAT...", "Rp5.500"),
        Obat("Insektisida", "Untuk mengendalikan hama, seperti Pounce, B...", "Rp6.500")

    )

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
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        rekomendasiObatAdapter = RekomendasiObatAdapter(rekomendasiObatList, requireActivity())
        recyclerView.adapter = rekomendasiObatAdapter
        return root
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