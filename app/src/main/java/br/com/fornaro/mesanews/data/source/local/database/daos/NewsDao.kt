package br.com.fornaro.mesanews.data.source.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fornaro.mesanews.data.source.local.database.entities.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT isFavorite FROM news WHERE user = :user AND title = :title")
    suspend fun selectFavorite(user: String, title: String): Boolean?

    @Query("SELECT * FROM news WHERE user = :user AND title = :title")
    suspend fun selectNews(user: String, title: String): NewsEntity?

    @Query("UPDATE news SET isFavorite = :isFavorite WHERE user = :user AND title = :title")
    suspend fun updateFavorite(user: String, title: String, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)
}