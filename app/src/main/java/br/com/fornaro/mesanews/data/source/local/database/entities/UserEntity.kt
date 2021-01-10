package br.com.fornaro.mesanews.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val email: String,
    val token: String
)
