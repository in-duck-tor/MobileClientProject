package com.ithirteeng.secondpatternsclientproject.domain.theme.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource

class FetchApplicationThemeUseCase(
    private val remoteDatasource: ThemeRemoteDatasource,
    private val localDatasource: ThemeLocalDatasource,
) {

    suspend operator fun invoke(login: String) = provideResult {
        val remoteTheme = remoteDatasource.getTheme(login)
        val localTheme = localDatasource.getCurrentTheme()

        if (remoteTheme == null) {
            remoteDatasource.updateTheme(login, localTheme)
        } else {
            if (!localDatasource.isUpdated()) {
                remoteDatasource.updateTheme(login, localTheme)
                localDatasource.setIsUpdated(true)
            } else {
                if (localTheme != remoteTheme) {
                    localDatasource.setTheme(remoteTheme)
                }
            }
        }
    }
}