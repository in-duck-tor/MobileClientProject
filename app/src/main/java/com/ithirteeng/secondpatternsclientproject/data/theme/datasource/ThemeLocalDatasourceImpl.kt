package com.ithirteeng.secondpatternsclientproject.data.theme.datasource

import com.ithirteeng.secondpatternsclientproject.data.theme.storage.ApplicationThemeStorage
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ThemeLocalDatasourceImpl(
    private val storage: ApplicationThemeStorage,
) : ThemeLocalDatasource {

    private val themeFlow = MutableStateFlow(storage.getTheme())

    override fun observeTheme(): StateFlow<Theme> {
        return themeFlow
    }

    override fun getCurrentTheme(): Theme {
        return storage.getTheme()
    }

    override fun setTheme(theme: Theme) {
        storage.setTheme(theme = theme)
        themeFlow.update { storage.getTheme() }
    }

    override fun isUpdated(): Boolean {
        return storage.getIsUpdated()
    }

    override fun setIsUpdated(isUpdated: Boolean) {
        return storage.setIsUpdated(isUpdated)
    }
}