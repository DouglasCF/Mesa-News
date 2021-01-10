package br.com.fornaro.mesanews.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.fornaro.mesanews.data.source.local.database.daos.UserDao
import br.com.fornaro.mesanews.data.source.local.database.entities.UserEntity
import br.com.fornaro.mesanews.domain.exceptions.UserNotLoggedInException

class AuthenticationLocalDataSource(
    context: Context,
    private val userDao: UserDao
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var email: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)
        set(value) {
            sharedPreferences.edit { putString(EMAIL_KEY, value) }
        }

    suspend fun getToken() =
        email?.let { userDao.selectToken(it) } ?: throw UserNotLoggedInException()

    suspend fun saveUser(email: String, token: String) {
        val user = UserEntity(email, token)
        userDao.insertUser(user)
        this.email = email
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "prefs_mesa_app"
        private const val EMAIL_KEY = "email_key"
    }
}