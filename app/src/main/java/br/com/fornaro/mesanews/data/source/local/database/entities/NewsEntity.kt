package br.com.fornaro.mesanews.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val user: String,
    val title: String,
    val isFavorite: Boolean
)
