package com.ithirteeng.secondpatternsclientproject.domain.theme.usecase

import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme

class SetApplicationThemeUseCase(
    private val localDatasource: ThemeLocalDatasource
) {

    operator fun invoke(theme: Theme) = localDatasource.setTheme(theme)
}