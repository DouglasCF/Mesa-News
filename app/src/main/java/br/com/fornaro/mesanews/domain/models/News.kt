package br.com.fornaro.mesanews.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class News(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val publishedAt: Calendar,
    val isHighlight: Boolean,
    val url: String,
    val imageUrl: String,
    var isFavorite: Boolean = false
) : Parcelable
