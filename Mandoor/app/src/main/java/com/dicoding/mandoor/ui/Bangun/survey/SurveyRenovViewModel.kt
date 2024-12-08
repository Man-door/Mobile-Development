package com.dicoding.mandoor.ui.Bangun.survey

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyRenovViewModel : ViewModel() {

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    private val _selectedBitmap = MutableLiveData<Bitmap?>()
    val selectedBitmap: LiveData<Bitmap?> get() = _selectedBitmap

    private val _rating = MutableLiveData<Int?>()
    val rating: LiveData<Int?> get() = _rating

    private val _pengalaman = MutableLiveData<Int?>()
    val pengalaman: LiveData<Int?> get() = _pengalaman

    private val _portofolio = MutableLiveData<Int?>()
    val portofolio: LiveData<Int?> get() = _portofolio

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun setBitmap(bitmap: Bitmap?) {
        _selectedBitmap.value = bitmap
    }

    fun setSurveyData(rating: Int, pengalaman: Int, portofolio: Int) {
        _rating.value = rating
        _pengalaman.value = pengalaman
        _portofolio.value = portofolio
    }

    fun saveSurveyData(): Boolean {
        return try {
            // Simpan data ke server atau database lokal
            // Gunakan _rating, _pengalaman, dan _portofolio jika diperlukan
            true
        } catch (e: Exception) {
            false
        }
    }
}
