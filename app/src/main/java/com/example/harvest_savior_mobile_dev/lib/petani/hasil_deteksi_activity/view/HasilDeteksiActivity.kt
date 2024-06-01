package com.example.harvest_savior_mobile_dev.lib.petani.hasil_deteksi_activity.view

import android.Manifest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.databinding.ActivityHasilDeteksiBinding
import com.example.harvest_savior_mobile_dev.lib.petani.hasil_deteksi_activity.viewModel.HasilDeteksiViewModel
import com.example.harvest_savior_mobile_dev.util.adapter.HasilDeteksiRekomObatAdapter
import com.example.harvest_savior_mobile_dev.util.adapter.RekomendasiObatAdapter

class HasilDeteksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilDeteksiBinding
    private lateinit var viewModel : HasilDeteksiViewModel
    private var currentImageUri: Uri? = null
    private lateinit var image : Uri

    private lateinit var recyclerView: RecyclerView
    private lateinit var rekomendasiObatAdapter: HasilDeteksiRekomObatAdapter
    private val rekomendasiObatList = listOf(
        Obat("Fungisida", "Untuk mengendalikan penyakit, seperti NOPAT...", "Rp5.500"),
        Obat("Insektisida", "Untuk mengendalikan hama, seperti Pounce, B...", "Rp6.500")

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDeteksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = intent.getParcelableExtra<Uri>(EXTRA_CAMERAX_IMAGE)!!

        binding.ivHasilDeteksi.setImageURI(image)

        recyclerView = binding.recyclerViewRekomObatHasilDeteksi
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        rekomendasiObatAdapter = HasilDeteksiRekomObatAdapter(rekomendasiObatList, this)
        recyclerView.adapter = rekomendasiObatAdapter
    }

    companion object {
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        private const val TAG = "HasilDeteksiActivity"
        private const val REQUEST_PICK_PHOTO = 1
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}