package br.com.fornaro.mesanews.data.source.remote.response

import com.squareup.moshi.Json

data class DataResponse(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    @Json(name = "published_at") val publishedAt: String,
    val highlight: Boolean,
    val url: String,
    @Json(name = "image_url") val imageUrl: String
)
