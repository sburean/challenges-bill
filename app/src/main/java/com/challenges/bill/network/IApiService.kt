package com.challenges.bill.network

import com.challenges.bill.model.ImagePage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("/api/?key=25943873-b3fceda0a6c2bc909346ff60a")
    fun getImagePage(@Query("q") searchQuery: String = "apple", pageNumber: Int = 1, perPage: Int = 20) : Call<ImagePage>

    @GET() //todo: fetch from url to get .jpg
    fun getImageData(imageUrl: String): Call<ResponseBody>

}