package br.com.fornaro.mesanews.data.source.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fornaro.mesanews.data.source.local.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT token FROM users WHERE email = :email")
    suspend fun selectToken(email: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)
}