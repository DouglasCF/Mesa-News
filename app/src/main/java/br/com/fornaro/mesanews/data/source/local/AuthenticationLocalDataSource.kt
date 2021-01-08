package br.com.fornaro.mesanews.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthenticationLocalDataSource(
    context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var token: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)
        set(value) {
            sharedPreferences.edit { putString(TOKEN_KEY, value) }
        }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "prefs_mesa_app"
        private const val TOKEN_KEY = "token_key"
    }
}