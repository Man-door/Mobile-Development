package com.dicoding.mandoor.ui.Bangun.survey

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dicoding.mandoor.R
import com.dicoding.mandoor.ui.Bangun.recommend.RecommendActivity

class SurveyRenovActivity : AppCompatActivity() {

    private val REQUEST_CODE_GALLERY = 1001
    private val REQUEST_CODE_CAMERA = 1002

    private val surveyRenovViewModel: SurveyRenovViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_renov)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarrenov)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val simpanButton = findViewById<Button>(R.id.btnSimpan)
        simpanButton.setOnClickListener {
            // Navigasi ke RecommendActivity
            surveyRenovViewModel.navigateToRecommend()
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }

        val galleryButton = findViewById<Button>(R.id.btn_gallery)
        galleryButton.setOnClickListener {
            openGallery()
        }

        val cameraButton = findViewById<Button>(R.id.btn_camera)
        cameraButton.setOnClickListener {
            openCamera()
        }

        // Mengamati perubahan data gambar dari ViewModel
        surveyRenovViewModel.selectedImageUri.observe(this, Observer { uri ->
            // Handle URI gambar jika diperlukan
            if (uri != null) {
                // Misalnya update UI atau proses gambar
            }
        })

        surveyRenovViewModel.selectedBitmap.observe(this, Observer { bitmap ->
            // Handle Bitmap gambar jika diperlukan
            if (bitmap != null) {
                // Misalnya update UI atau proses bitmap
            }
        })
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
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImageUri = data?.data
                    surveyRenovViewModel.setImageUri(selectedImageUri)
                }
                REQUEST_CODE_CAMERA -> {
                    val photo = data?.extras?.get("data") as? Bitmap
                    surveyRenovViewModel.setBitmap(photo)
                }
            }
        }
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

