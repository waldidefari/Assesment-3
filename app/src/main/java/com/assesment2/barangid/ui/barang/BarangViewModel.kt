package com.assesment2.barangid.ui.barang

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.assesment2.barangid.network.ApiStatus
import com.assesment2.barangid.network.BarangApi
import com.assesment2.barangid.network.BarangApiService
import com.assesment2.barangid.network.UpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BarangViewModel : ViewModel() {
    private val data = MutableLiveData<List<DataBarang>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(BarangApiService.service.getBarang())
                status.postValue(ApiStatus.SUCCES)
            } catch (e: Exception) {
                status.postValue(ApiStatus.FAILED)
                Log.d("BarangViewModel", "Failure: ${e.message}")
            }
        }
    }
    fun getData(): LiveData<List<DataBarang>> = data
    fun getStatus(): LiveData<ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}