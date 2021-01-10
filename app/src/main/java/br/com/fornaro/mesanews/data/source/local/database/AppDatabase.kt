package br.com.fornaro.mesanews.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.fornaro.mesanews.data.source.local.database.daos.UserDao
import br.com.fornaro.mesanews.data.source.local.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}