package com.challenges.bill.dataAccess

import com.challenges.bill.model.ImagePage
import okhttp3.ResponseBody

interface IImageRepo {

    suspend fun getImagePage(): Result<ImagePage>

    suspend fun getImageThumbnail(url: String): Result<ResponseBody>
}