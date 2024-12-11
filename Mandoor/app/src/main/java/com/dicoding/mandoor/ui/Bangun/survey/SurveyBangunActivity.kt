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
import android.view.MenuItem
import android.widget.*
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
    private lateinit var layananSpinner: Spinner

    private val layananOptions = listOf(
        "Pilih Layanan", "Atap Pemasangan", "Renovasi Rumah", "Bangunan Kontraktor",
        "Jendela Kusen Pintu", "Partisi Pembatas Ruangan", "Kabinet", "Instalasi Kanopi",
        "Pengecatan", "Teralis", "Bengkel Las", "Service Sofa", "Plafon", "Jasa Pertukangan",
        "(Borongan) Jasa Pertukangan", "Taman Tukang", "Jendela Pemasangan Pintu", "Pagar Pemasangan",
        "Pagar Perbaikan", "Pengeboran Sumur", "Waterproofing", "Pemasangan Wallpaper", "Railing",
        "Lantai Pemasangan", "Designer Interior", "Partisi", "Beton Injeksi", "Atap Perbaikan",
        "Lantai Perbaikan", "Kayu Tukang", "Epoxy Lantai", "Lampu Pemasangan", "AC Pemasangan",
        "Kelistrikan", "Borongan Ledeng Tukang", "Ledeng Tukang", "Cuci Mesin Service", "Kulkas Service",
        "Service TV", "AC Service", "AC Perusahaan Service", "Ikan Kolam", "Kolam Renang", "Arsitek",
        "Kontraktor Pameran", "Gazebo", "(Harian) Jasa Pertukangan", "Pemasangan TV", "Lampu Service",
        "Harian Jasa Tukang", "Air Pompa Service", "Alarm CCTV", "Air Pemanas Service", "Control Pest",
        "Gorden", "Sedot WC", "Ahli Kunci", "Rental Sound System", "Booth Photo Rental",
        "Cleaning Service", "Gas Kompor Service", "Pindahan", "Catering Event", "Event Organizer",
        "Cuci Karpet", "Cuci Sofa"
    )

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

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            layananOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        layananSpinner.adapter = adapter

        tanggalBangunEditText.setOnClickListener {
            showDatePickerDialog()
        }

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

        surveyBangunViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                loadingAdapter.showLoading()
            } else {
                loadingAdapter.dismissLoading()
            }
        })

        surveyBangunViewModel._successMessage.observe(this, Observer { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })

        surveyBangunViewModel._errorMessage.observe(this, Observer { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            tanggalBangunEditText.setText(formattedDate)
        }, year, month, day).show()
    }

    private fun saveSurvey() {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Melanjutkan tanpa token.", Toast.LENGTH_SHORT).show()
            return
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
        val selectedPengalaman = pengalamanGroup.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id)?.text?.toString()?.toIntOrNull() ?: 0
        }
        val selectedPortofolio = portofolioGroup.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id)?.text?.toString()?.toIntOrNull() ?: 0
        }
        val selectedLayanan = layananSpinner.selectedItem.toString()
        val selectedDate = tanggalBangunEditText.text.toString()
        val selectedImageUri = surveyBangunViewModel.selectedImageUri.value
        val rangeHarga = rangeHargaEditText.text.toString()
        val deskripsi = deskripsiEditText.text.toString()
        val alamatPengerjaan = alamatPengerjaanEditText.text.toString()

        if (selectedImageUri == null) {
            Toast.makeText(this, "Harap pilih gambar!", Toast.LENGTH_SHORT).show()
            return
        }

        surveyBangunViewModel.saveSurveyData(
            token,
            selectedRating,
            selectedPengalaman,
            selectedPortofolio,
            selectedLayanan,
            rangeHarga,
            deskripsi,
            alamatPengerjaan,
            selectedDate,
            selectedImageUri,
            onSuccess = {
                // Pindah ke RecommendActivity setelah berhasil
                val intent = Intent(this, RecommendActivity::class.java)
                startActivity(intent)
            },
            onError = { errorMessage ->
                // Tampilkan pesan error
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
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
