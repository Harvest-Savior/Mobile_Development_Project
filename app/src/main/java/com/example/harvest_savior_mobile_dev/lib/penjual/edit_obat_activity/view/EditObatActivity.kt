package com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityEditObatBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.viewmodel.EditObatViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view.HomePenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view.TambahObatActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.viewmodel.TambahObatViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.createCustomTempFile
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.example.harvest_savior_mobile_dev.util.getImageUri
import java.io.File
import java.io.FileOutputStream

class EditObatActivity : AppCompatActivity() {
    private var _binding: ActivityEditObatBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditObatViewModel

    private var currentImageUri: Uri? = null

    private lateinit var pref : LoginStorePreference
    private lateinit var medicineStoreRepository: MedicineStoreRepository

    private var token2 : String? = null
    private var namToko : String? = null
    private var emailToko : String? = null

    private var idObat : String? = null
    private var namaObat : String? = null
    private var descObat : String? = null
    private var hargaObat : Int? = null
    private var stokObat : Int? = null

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val apiService = ApiConfig.getApiService()
        pref = LoginStorePreference.getInstance(application.datastoreStore)
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)

        val editViewModelFactory = LoginStoreVMFactory(medicineStoreRepository,pref)
        viewModel = ViewModelProvider(this, editViewModelFactory).get(EditObatViewModel::class.java)
        token2 = intent.getStringExtra("token")
        namToko = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")
        idObat = intent.getStringExtra("idObat")
        Log.d(TAG,"idObat : $idObat")
        Log.d(TAG,"token : $token2")



        viewModel.editObatResult.observe(this) {
            it.onSuccess {response ->
                response.data.let {
                    val intent = Intent(this, HomePenjualActivity::class.java)
                    intent.putExtra("token", token2)
                    intent.putExtra("namaToko",namToko)
                    intent.putExtra("email",emailToko)
                    AnimationUtil.startActivityWithSlideAnimation(this, intent)
                    finish()
                    Toast.makeText(this,"Berhasil merubah obat anda", Toast.LENGTH_SHORT).show()
                    binding.btnSimpan.isEnabled = false
                }

            }.onFailure { e ->
                Log.d(TAG,"onfailure edit : $e")
                Toast.makeText(this,"Gagal merubah obat anda", Toast.LENGTH_SHORT).show()
                binding.btnSimpan.isEnabled = true
            }
        }

        binding.btnSimpan.setOnClickListener {
            val desk = binding.etDeskripsiObat.text.toString()
            viewModel.editObat(token2!!,idObat!!, desk)
        }

        binding.btnChangeImage.setOnClickListener { startCamera() }
        binding.btnOpenGallery.setOnClickListener { startGallery() }
        binding.ivProduk.setOnClickListener { startCamera() }


    }

    private fun convertImageViewToFile(imageView: ImageView, fileName: String): File {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file
    }

    private fun compressImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var quality = 100
        val compressedFile = createCustomTempFile(application)
        do {
            val outputStream = FileOutputStream(compressedFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            quality -= 5
        } while (compressedFile.length() > 1_000_000 && quality > 0)

        return compressedFile
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivProduk.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "EditObatActivity"
    }
}