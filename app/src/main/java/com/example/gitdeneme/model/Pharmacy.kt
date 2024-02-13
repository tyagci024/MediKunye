package com.example.gitdeneme.model

import com.google.gson.annotations.SerializedName

data class Pharmacy(
    @SerializedName("pharmacyName")
    val phName: String,

    @SerializedName("address")
    val phAdress: String,

    @SerializedName("phone")
    val number: String
)
