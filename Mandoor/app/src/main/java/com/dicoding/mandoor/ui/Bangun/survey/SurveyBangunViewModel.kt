package com.dicoding.mandoor.ui.Bangun.survey

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.api.SurveyRequest
import com.dicoding.mandoor.response.SurveyPOSTResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyBangunViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    private val _selectedBitmap = MutableLiveData<Bitmap?>()
    val selectedBitmap: LiveData<Bitmap?> get() = _selectedBitmap

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun saveSurveyData(
        token: String,
        rating: Int,
        pengalaman: Int,
        portofolio: Int,
        selectedLayanan: String,
        selectedDate: String,
        selectedImageUri: Uri,
        rangeHarga: String,
        deskripsi: String,
        alamatPengerjaan: String,
    ) {

        _loading.value = true

        val surveyRequest = SurveyRequest(
            Rating = rating.toString(),
            Pengalaman = pengalaman.toString(),
            Portofolio = portofolio.toString(),
            Alamat = alamatPengerjaan,
            layanan_lain = selectedLayanan,
            Tanggal = selectedDate,
            Budget = rangeHarga,
            Deskripsi = deskripsi,
            image = selectedImageUri
        )

        ApiConfig.mainInstance.sendSurvey("Bearer $token", surveyRequest)
            .enqueue(object : Callback<SurveyPOSTResponse> {
                override fun onResponse(
                    call: Call<SurveyPOSTResponse>,
                    response: Response<SurveyPOSTResponse>
                ) {
                    _loading.value = false
                    if (response.isSuccessful) {
                        _successMessage.value =
                            "Survey berhasil disimpan: ${response.body()?.message}"
                    } else {
                        _errorMessage.value = "Gagal menyimpan survey: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<SurveyPOSTResponse>, t: Throwable) {
                    _loading.value = false
                    _errorMessage.value = "Gagal terhubung ke server: ${t.message}"
                }
            })
    }
}
