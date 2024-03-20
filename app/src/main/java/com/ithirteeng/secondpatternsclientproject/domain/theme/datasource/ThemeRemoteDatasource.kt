package com.ithirteeng.secondpatternsclientproject.domain.theme.datasource

import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme

interface ThemeRemoteDatasource {

    suspend fun getTheme(login: String): Theme?

    fun updateTheme(login: String, newTheme: Theme)
}