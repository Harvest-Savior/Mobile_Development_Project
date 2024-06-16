package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.FragmentDeteksiBinding
import com.example.harvest_savior_mobile_dev.lib.petani.camera_activity.view.CameraPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.hasil_deteksi_activity.view.HasilDeteksiActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DeteksiFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var currentImageUri: Uri? = null
    private var _binding: FragmentDeteksiBinding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentDeteksiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTakePhoto.setOnClickListener { startCameraX() }
        binding.btnPilihGambar.setOnClickListener { choosePhoto() }

    }

    private fun startCameraX() {
        val intent = Intent(activity, CameraPetaniActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CameraPetaniActivity.CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraPetaniActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_PHOTO)

    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            val intent = Intent(activity, HasilDeteksiActivity::class.java)
            intent.putExtra(EXTRA_CAMERAX_IMAGE, it)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK && data != null) {

            val photoUri = data.data

        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeteksiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val REQUEST_PICK_PHOTO = 1
        private const val TAG = "MainActivity"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
    }
}