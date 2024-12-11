package com.dicoding.mandoor.ui.Bangun.survey

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.response.SurveyPOSTResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyBangunViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    private val _selectedBitmap = MutableLiveData<Bitmap?>()
    val selectedBitmap: LiveData<Bitmap?> get() = _selectedBitmap

    val _successMessage = MutableLiveData<String>()

    val _errorMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun setBitmap(bitmap: Bitmap?) {
        _selectedBitmap.value = bitmap
    }

    fun saveSurveyData(
        token: String,
        rating: Int,
        pengalaman: Int,
        portofolio: Int,
        selectedLayanan: String,
        rangeHarga: String,
        deskripsi: String,
        alamatPengerjaan: String,
        selectedDate: String,
        imagePath: Uri,
        onSuccess: () -> Unit, // Callback sukses
        onError: (String) -> Unit // Callback error
    ) {
        _loading.postValue(true) // Tampilkan loading

        val context = getApplication<Application>()
        val contentResolver = context.contentResolver

        val imageFile = contentResolver.openInputStream(imagePath)?.use { inputStream ->
            inputStream.readBytes()
        }

        if (imageFile == null) {
            _loading.postValue(false)
            onError("Gagal membaca file gambar.")
            return
        }

        val imageBody = MultipartBody.Part.createFormData(
            "image",
            "image.jpg",
            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        )

        val RatingBody = RequestBody.create("text/plain".toMediaTypeOrNull(), rating.toString())
        val PengalamanBody = RequestBody.create("text/plain".toMediaTypeOrNull(), pengalaman.toString())
        val PortofolioBody = RequestBody.create("text/plain".toMediaTypeOrNull(), portofolio.toString())
        val layananBody = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedLayanan)
        val BudgetBody = RequestBody.create("text/plain".toMediaTypeOrNull(), rangeHarga)
        val DeskripsiBody = RequestBody.create("text/plain".toMediaTypeOrNull(), deskripsi)
        val AlamatBody = RequestBody.create("text/plain".toMediaTypeOrNull(), alamatPengerjaan)
        val TanggalBody = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedDate)

        val apiService = ApiConfig.surveyInstance

        apiService.sendSurvey(
            "Bearer $token",
            RatingBody,
            PengalamanBody,
            PortofolioBody,
            layananBody,
            BudgetBody,
            DeskripsiBody,
            AlamatBody,
            TanggalBody,
            imageBody
        ).enqueue(object : Callback<SurveyPOSTResponse> {
            override fun onResponse(call: Call<SurveyPOSTResponse>, response: Response<SurveyPOSTResponse>) {
                _loading.postValue(false) // Sembunyikan loading
                if (response.isSuccessful) {
                    onSuccess() // Callback sukses
                } else {
                    onError("Gagal menyimpan survey: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SurveyPOSTResponse>, t: Throwable) {
                _loading.postValue(false) // Sembunyikan loading
                onError("Gagal terhubung ke server: ${t.message}")
            }
        })
    }

}
