package com.example.gitdeneme.model

data class ApiResponse(
    val status: String,
    val message: String,
    val data: List<Pharmacy>
)