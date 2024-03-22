package com.ithirteeng.secondpatternsclientproject.domain.theme.usecase

import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource

class ObserveApplicationThemeUseCase(
    private val localDatasource: ThemeLocalDatasource
) {

    operator fun invoke() = localDatasource.observeTheme()
}