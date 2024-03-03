package com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

interface MainClientEffect : BaseEffect {

    data class ShowError(
        @StringRes val stringResource: Int,
    ) : MainClientEffect
}
