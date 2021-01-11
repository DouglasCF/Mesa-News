package br.com.fornaro.mesanews.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.fornaro.mesanews.domain.enums.FeedFilter

open class PreferenceLocalDataSource(
    context: Context
) {

    protected val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var filter: String
        get() = sharedPreferences.getString(FILTER_KEY, FeedFilter.DATE.name)
            ?: FeedFilter.DATE.name
        set(value) {
            sharedPreferences.edit { putString(FILTER_KEY, value) }
        }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "prefs_mesa_app"
        private const val FILTER_KEY = "filter_key"
    }
}