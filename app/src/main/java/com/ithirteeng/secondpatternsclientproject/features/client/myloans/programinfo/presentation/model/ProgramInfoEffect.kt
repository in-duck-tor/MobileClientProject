package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface ProgramInfoEffect : BaseEffect {

    data class ShowError(val message: String) : ProgramInfoEffect

    data class NavigateToCreateLoanScreen(val programId: Int) : ProgramInfoEffect
}