package com.ithirteeng.secondpatternsclientproject.data.theme.storage

import android.content.Context
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme

class ApplicationThemeStorage(
    context: Context,
) {

    private val sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    fun setTheme(theme: Theme) {
        sharedPreferences.edit()
            .putString(THEME_KEY, theme.name)
            .apply()
    }

    fun getTheme(): Theme {
        val themeString = sharedPreferences.getString(THEME_KEY, Theme.AUTO.name)
        return themeString?.let { Theme.valueOf(it) } ?: Theme.AUTO
    }

    private companion object {

        const val THEME_KEY = "theme key"
        const val STORAGE_NAME = "theme local storage"
    }
}