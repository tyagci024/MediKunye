package com.example.gitdeneme.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gitdeneme.model.ApiResponse
import com.example.gitdeneme.model.Pharmacy
import com.example.gitdeneme.servicePharmacy.PharmacyApiService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PharmacyViewModel(application: Application) : AndroidViewModel(application) {


    private val pharmacyApiService = PharmacyApiService()
    private val disposable = CompositeDisposable()


    val pharmacyValue = MutableLiveData<List<Pharmacy>>()







    fun getDataFromApi(latitude:Double,longitude: Double) {


        disposable.add(
            pharmacyApiService.getData(latitude, longitude ,"cw2RpbZRd24tL273tYiJY8vQzXCyCR0waDaMMC6rG3HeePsEG1Wa66CsPjI8")
                .subscribeOn(Schedulers.newThread())//UI thread'ini bloke etmiyoz
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponse>() {
                    override fun onSuccess(response: ApiResponse) {
                        pharmacyValue.value = response.data
                        Log.d("PharmacyViewModel", "API'dan çekilen veriler: ${response.data.size} adet eczane bulundu.")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Log.e("PharmacyViewModel", "API'dan veri çekme hatası: ${e.message}")
                    }
                })
        )

    }

    fun updateLocation(latitude: Double, longitude: Double) {
        getDataFromApi(latitude, longitude)
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}