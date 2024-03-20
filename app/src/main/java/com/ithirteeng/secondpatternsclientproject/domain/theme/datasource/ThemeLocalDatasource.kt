package com.ithirteeng.secondpatternsclientproject.domain.theme.datasource

import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeLocalDatasource {

    fun observeTheme(): Flow<Theme>

    fun setTheme(theme: Theme)
}