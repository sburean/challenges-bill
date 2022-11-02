package com.challenges.bill.dataAccess

import com.challenges.bill.model.ImageData
import com.challenges.bill.model.ImagePage
import com.challenges.bill.network.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class ImageRepo(
    private val apiService: IApiService
) : IImageRepo {

    //todo: search query param from view layer, and page data from VM
    override suspend fun getImagePage(): Result<ImagePage> = withContext(Dispatchers.IO) {
        with(this@ImageRepo) {
            val call = this.apiService.getImagePage()
            call.execute().let { response ->
                val responseBody: ImagePage? = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("image page response error"))
                }
            }
        }
    }

    override suspend fun getImageThumbnail(url: String): Result<ResponseBody> = withContext(Dispatchers.IO) {
        with(this@ImageRepo) {
            val call = this.apiService.getImageData(url)
            call.execute().let { response ->
                val responseBody: ResponseBody? = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("image data response error"))
                }
            }
        }
    }

}