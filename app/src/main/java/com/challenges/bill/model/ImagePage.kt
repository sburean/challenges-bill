package com.challenges.bill.model

data class ImagePage(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageData>
)