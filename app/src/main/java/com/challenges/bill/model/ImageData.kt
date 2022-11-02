package com.challenges.bill.model

data class ImageData(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: List<String>,
    val previewURL: String
)