package br.com.fornaro.mesanews.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: Long,
    val user: String,
    val title: String,
    val isFavorite: Boolean
)
