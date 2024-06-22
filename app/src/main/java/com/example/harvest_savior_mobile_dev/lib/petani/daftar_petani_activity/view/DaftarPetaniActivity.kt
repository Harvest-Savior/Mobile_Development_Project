package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.RegisterViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view.DaftarPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.createCustomTempFile
import com.example.harvest_savior_mobile_dev.util.getImageUri
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class DaftarPetaniActivity : AppCompatActivity() {
    private var _binding: ActivityDaftarPetaniBinding? = null
    private val binding get() = _binding!!


    private lateinit var image : Uri
    private var currentImageUri: Uri? = null


    private lateinit var viewModel : DaftarPetaniViewModel
    private lateinit var userFarmerRepository: UserFarmerRepository

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
        _binding = ActivityDaftarPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()
        userFarmerRepository = UserFarmerRepository(apiService,apiService2)


        val daftarViewModelFactory = RegisterViewModelFactory(userFarmerRepository)
        viewModel = ViewModelProvider(this, daftarViewModelFactory).get(DaftarPetaniViewModel::class.java)

        binding.btnRegisterPetani.setOnClickListener {
            regiterFarmer()
        }

        binding.btnChangeImage.setOnClickListener {
            startCamera()
        }
        binding.btnOpenGallery.setOnClickListener {
            startGallery()
        }

        binding.ivProfilFarmer.setOnClickListener {
            startCamera()
        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }

        viewModel.registerResult.observe(this) {
            it.onSuccess {
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginPetaniActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }.onFailure {e ->
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun regiterFarmer() {
        val namaTokoText = binding.etNama.text.toString()
        val namaToko = namaTokoText.toRequestBody("text/plain".toMediaType())

        val emailText = binding.etEmail.text.toString()
        val email = emailText.toRequestBody("text/plain".toMediaType())

        val passText = binding.etPw.text.toString()
        val pass = passText.toRequestBody("text/plain".toMediaType())

        val confirmPassText = binding.etConfirmPw.text.toString()
        val confirmPass = confirmPassText.toString().toRequestBody("text/plain".toMediaType())

        val photo = binding.ivProfilFarmer

        val photoFile2 = convertImageViewToFile(photo, "gambar.jpeg")
        val compressedPhotoFile = photoFile2?.let { compressImageFile(it) }
        val requestImageFile = compressedPhotoFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            compressedPhotoFile?.name,
            requestImageFile!!
        )

        if (isImageViewEmpty(photo)) {
            Snackbar.make(
                window.decorView.rootView,
                "Foto profil harus diisi",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        if (namaTokoText.isNotEmpty()  && passText.isNotEmpty() && confirmPassText.isNotEmpty() ) {
            if (isValidEmail(emailText)) {
                binding.etEmail.error = null
                if (passText.length >= 8) {
                    binding.etEmail.error = null
                    if (passText == confirmPassText ) {
                        binding.etConfirmPw.error = null
                        viewModel.registerFarmer(namaToko,email,pass,confirmPass,imageMultipart)
                    } else {
                        binding.etConfirmPw.error = "Cek kembali konfirmasi password anda"
                    }
                } else {
                    binding.etPw.error = "Password harus lebih dari 8 karakter"
                }
            } else {
                binding.etEmail.error = "Input email tidak valid"
            }
        } else {
            Snackbar.make(
                window.decorView.rootView,
                R.string.error_regist_allnull,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressDaftarPetani.visibility = View.VISIBLE
        } else {
            binding.progressDaftarPetani.visibility = View.GONE
        }
    }


    private fun convertImageViewToFile(imageView: ImageView, fileName: String): File? {
        val drawable = imageView.drawable
        val bitmap: Bitmap?

        when (drawable) {
            is BitmapDrawable -> {
                bitmap = drawable.bitmap
            }
            is VectorDrawable -> {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
            else -> {
                Log.e(TAG, "Tipe drawable tidak didukung: ${drawable.javaClass.simpleName}")
                return null
            }
        }

        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
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

    private fun isImageViewEmpty(imageView: ImageView): Boolean {
        return imageView.drawable == null
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
            binding.ivProfilFarmer.setImageURI(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AnimationUtil.finishActivityWithSlideAnimation(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "DaftarPetaniActivity"
        private const val TOKEN_DETEKSI = "tokenDeteksi"
    }
}