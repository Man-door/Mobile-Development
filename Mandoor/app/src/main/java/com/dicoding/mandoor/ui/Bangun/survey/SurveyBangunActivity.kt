package com.dicoding.mandoor.ui.Bangun.survey

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dicoding.mandoor.R
import com.dicoding.mandoor.adapter.LoadingAdapter
import com.dicoding.mandoor.ui.Bangun.recommend.RecommendActivity
import java.io.ByteArrayOutputStream
import java.util.Calendar

class SurveyBangunActivity : AppCompatActivity() {

    private val REQUEST_CODE_GALLERY = 1001
    private val REQUEST_CODE_CAMERA = 1002
    private val CAMERA_PERMISSION_CODE = 1003

    private val surveyBangunViewModel: SurveyBangunViewModel by viewModels()
    private lateinit var loadingAdapter: LoadingAdapter
    private lateinit var tanggalBangunEditText: EditText

    private val layananOptions = listOf(
        "Pilih Layanan",
        "Atap Pemasangan",
        "Renovasi Rumah",
        "Bangunan Kontraktor",
        "Jendela Kusen Pintu",
        "Partisi Pembatas Ruangan",
        "Kabinet",
        "Instalasi Kanopi",
        "Pengecatan",
        "Teralis",
        "Bengkel Las",
        "Service Sofa",
        "Plafon",
        "Jasa Pertukangan",
        "(Borongan) Jasa Pertukangan",
        "Taman Tukang",
        "Jendela Pemasangan Pintu",
        "Pagar Pemasangan",
        "Pagar Perbaikan",
        "Pengeboran Sumur",
        "Waterproofing",
        "Pemasangan Wallpaper",
        "Railing",
        "Lantai Pemasangan",
        "Designer Interior",
        "Partisi",
        "Beton Injeksi",
        "Atap Perbaikan",
        "Lantai Perbaikan",
        "Kayu Tukang",
        "Epoxy Lantai",
        "Lampu Pemasangan",
        "AC Pemasangan",
        "Kelistrikan",
        "Borongan Ledeng Tukang",
        "Ledeng Tukang",
        "Cuci Mesin Service",
        "Kulkas Service",
        "Service TV",
        "AC Service",
        "AC Perusahaan Service",
        "Ikan Kolam",
        "Kolam Renang",
        "Arsitek",
        "Kontraktor Pameran",
        "Gazebo",
        "(Harian) Jasa Pertukangan",
        "Pemasangan TV",
        "Lampu Service",
        "Harian Jasa Tukang",
        "Air Pompa Service",
        "Alarm CCTV",
        "Air Pemanas Service",
        "Control Pest",
        "Gorden",
        "Sedot WC",
        "Ahli Kunci",
        "Rental Sound System",
        "Booth Photo Rental",
        "Cleaning Service",
        "Gas Kompor Service",
        "Pindahan",
        "Catering Event",
        "Event Organizer",
        "Cuci Karpet",
        "Cuci Sofa"
    )


    private lateinit var layananSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_bangun)

        loadingAdapter = LoadingAdapter(this)

        val ivSurveyBangun = findViewById<ImageView>(R.id.iv_survey_bangun)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarsurveybangun)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        layananSpinner = findViewById(R.id.spinner_layanan)

        tanggalBangunEditText = findViewById(R.id.ed_tanggal_bangun)
        tanggalBangunEditText.setOnClickListener {
            showDatePickerDialog()
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            layananOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        layananSpinner.adapter = adapter

        val simpanButton = findViewById<Button>(R.id.btnsimpanbangun)
        simpanButton.setOnClickListener {
            saveSurvey()
        }

        val galleryButton = findViewById<Button>(R.id.btn_gallery_bangun)
        galleryButton.setOnClickListener {
            openGallery()
        }

        val cameraButton = findViewById<Button>(R.id.btn_camera_bangun)
        cameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            } else {
                openCamera()
            }
        }

        surveyBangunViewModel.selectedImageUri.observe(this, Observer { uri ->
            if (uri != null) {
            }
        })

        surveyBangunViewModel.selectedBitmap.observe(this, Observer { bitmap ->
            if (bitmap != null) {
            }
        })
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
            tanggalBangunEditText.setText(formattedDate)
        }, year, month, day).show()
    }

    private fun saveSurvey() {
        loadingAdapter.showLoading()

        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login kembali.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Log.d("TokenDebug", "Token ditemukan: $token")
        }


        val ratingGroup = findViewById<RadioGroup>(R.id.rg_rating_bangun)
        val pengalamanGroup = findViewById<RadioGroup>(R.id.rg_pengalaman_bangun)
        val portofolioGroup = findViewById<RadioGroup>(R.id.rg_portofolio_bangun)
        val rangeHargaEditText = findViewById<EditText>(R.id.ed_rangebangun)
        val deskripsiEditText = findViewById<EditText>(R.id.ed_descbangun)
        val alamatPengerjaanEditText = findViewById<EditText>(R.id.ed_alamat_pengerjaan_bangun)

        val selectedRating = ratingGroup.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id)?.text?.toString()?.toIntOrNull() ?: 0
        }

        if (selectedRating == 0) {
            Toast.makeText(this, "Harap pilih rating!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val selectedPengalaman = pengalamanGroup.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id)?.text?.toString()?.toIntOrNull() ?: 0
        }

        if (selectedPengalaman == 0) {
            Toast.makeText(this, "Harap pilih pengalaman!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val selectedPortofolio = portofolioGroup.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id)?.text?.toString()?.toIntOrNull() ?: 0
        }

        if (selectedPortofolio == 0) {
            Toast.makeText(this, "Harap pilih portofolio!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val selectedLayanan = layananSpinner.selectedItem.toString()
        if (selectedLayanan == "Pilih Layanan") {
            Toast.makeText(this, "Harap pilih layanan!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val selectedDate = tanggalBangunEditText.text.toString()
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Harap pilih tanggal!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val selectedImageUri = surveyBangunViewModel.selectedImageUri.value
        if (selectedImageUri == null) {
            Toast.makeText(this, "Harap pilih gambar!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val rangeHarga = rangeHargaEditText.text.toString()
        if (rangeHarga.isEmpty()) {
            Toast.makeText(this, "Harap isi range harga!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val deskripsi = deskripsiEditText.text.toString()
        if (deskripsi.isEmpty()) {
            Toast.makeText(this, "Harap isi deskripsi!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        val alamatPengerjaan = alamatPengerjaanEditText.text.toString()
        if (alamatPengerjaan.isEmpty()) {
            Toast.makeText(this, "Harap isi alamat pengerjaan!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        if (selectedRating == 0 || selectedPengalaman == 0 || selectedPortofolio == 0 ||
            selectedLayanan == "Pilih Layanan" || selectedDate.isEmpty() || rangeHarga.isEmpty() ||
            deskripsi.isEmpty() || alamatPengerjaan.isEmpty() || selectedImageUri == null
        ) {
            Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
            loadingAdapter.dismissLoading()
            return
        }

        // Panggil ViewModel untuk mengirim data
        if (token != null) {
            surveyBangunViewModel.saveSurveyData(
                token = token,
                rating = selectedRating,
                pengalaman = selectedPengalaman,
                portofolio = selectedPortofolio,
                selectedLayanan = selectedLayanan,
                selectedDate = selectedDate,
                selectedImageUri = selectedImageUri,
                rangeHarga = rangeHarga,
                deskripsi = deskripsi,
                alamatPengerjaan = alamatPengerjaan
            )
        }
        surveyBangunViewModel.successMessage.observe(this) { message ->
            loadingAdapter.dismissLoading()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RecommendActivity::class.java))
        }
        surveyBangunViewModel.errorMessage.observe(this) { message ->
            loadingAdapter.dismissLoading()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val ivSurveyBangun = findViewById<ImageView>(R.id.iv_survey_bangun)
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        surveyBangunViewModel.setImageUri(selectedImageUri)
                        ivSurveyBangun.setImageURI(selectedImageUri)
                    } else {
                        Toast.makeText(this, "Gagal memuat gambar dari galeri", Toast.LENGTH_SHORT).show()
                    }
                }
                REQUEST_CODE_CAMERA -> {
                    val photo = data?.extras?.get("data") as? Bitmap
                    if (photo != null) {
                        val photoUri = getImageUri(this, photo)
                        surveyBangunViewModel.setImageUri(photoUri)
                        ivSurveyBangun.setImageBitmap(photo)
                    } else {
                        Toast.makeText(this, "Gagal mengambil gambar dari kamera", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getImageUri(context: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "title", null)
        return Uri.parse(path)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
