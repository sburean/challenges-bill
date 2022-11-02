package com.challenges.bill.utils

import com.challenges.bill.dataAccess.IImageRepo
import com.challenges.bill.dataAccess.ImageRepo
import com.challenges.bill.network.IApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitClient: Retrofit = Retrofit.Builder()
    .baseUrl("https://pixabay.com/api/?key=25943873-b3fceda0a6c2bc909346ff60a")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: IApiService = retrofitClient.create(IApiService::class.java)

val imageRepo: IImageRepo = ImageRepo(apiService)