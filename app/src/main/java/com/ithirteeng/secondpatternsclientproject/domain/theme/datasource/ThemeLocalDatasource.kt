package com.ithirteeng.secondpatternsclientproject.domain.theme.datasource

import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeLocalDatasource {

    fun observeTheme(): Flow<Theme>

    fun getCurrentTheme(): Theme

    fun setTheme(theme: Theme)

    fun isUpdated(): Boolean

    fun setIsUpdated(isUpdated: Boolean)
}