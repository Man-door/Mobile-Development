package com.dicoding.mandoor.ui.Bangun.survey

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyBangunViewModel : ViewModel() {

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    private val _selectedBitmap = MutableLiveData<Bitmap?>()
    val selectedBitmap: LiveData<Bitmap?> get() = _selectedBitmap

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun setBitmap(bitmap: Bitmap?) {
        _selectedBitmap.value = bitmap
    }

    fun navigateToRecommend() {
    }
}