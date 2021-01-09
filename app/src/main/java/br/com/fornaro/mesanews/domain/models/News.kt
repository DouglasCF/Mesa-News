package br.com.fornaro.mesanews.domain.models

import java.util.*

data class News(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val publishedAt: Calendar,
    val isHighlight: Boolean,
    val url: String,
    val imageUrl: String
)
