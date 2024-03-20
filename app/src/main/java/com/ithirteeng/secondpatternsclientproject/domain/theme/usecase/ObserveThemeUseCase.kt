package com.ithirteeng.secondpatternsclientproject.domain.theme.usecase

import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource

class ObserveThemeUseCase(
    private val localDatasource: ThemeLocalDatasource
) {

    operator fun invoke() = localDatasource.observeTheme()
}