package com.example.gitdeneme.servicePharmacy

import com.example.gitdeneme.model.ApiResponse
import com.example.gitdeneme.model.Pharmacy
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacyApi {

    @GET("pharmacies-on-duty/locations")
    fun getPharmacy(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("apiKey") apiKey: String = "cw2RpbZRd24tL273tYiJY8vQzXCyCR0waDaMMC6rG3HeePsEG1Wa66CsPjI8"
    ): Single<ApiResponse>
}