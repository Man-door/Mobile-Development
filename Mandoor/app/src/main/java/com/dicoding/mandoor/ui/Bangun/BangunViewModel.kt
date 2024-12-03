package com.dicoding.mandoor.ui.Bangun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BangunViewModel : ViewModel() {

    private val _serviceName = MutableLiveData<String>()
    val serviceName: LiveData<String> get() = _serviceName

    private val _serviceDescription = MutableLiveData<String>()
    val serviceDescription: LiveData<String> get() = _serviceDescription

    private val _serviceName2 = MutableLiveData<String>()
    val serviceName2: LiveData<String> get() = _serviceName2

    private val _serviceDescription2 = MutableLiveData<String>()
    val serviceDescription2: LiveData<String> get() = _serviceDescription2

    fun setServiceData() {
        _serviceName.value = "Bangun"
        _serviceDescription.value = "Deskripsi Bangun"
        _serviceName2.value = "Renovasi"
        _serviceDescription2.value = "Deskripsi Renovasi"
    }
}