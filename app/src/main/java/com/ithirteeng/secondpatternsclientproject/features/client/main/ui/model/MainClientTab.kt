package com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.R

sealed interface MainClientTab {

    @get:StringRes
    val titleResource: Int

    @get:DrawableRes
    val iconResource: Int

    data object MyLoans : MainClientTab {

        override val titleResource: Int
            get() = R.string.my_loans
        override val iconResource: Int
            get() = R.drawable.icon_my_loans
    }

    data object MyAccounts : MainClientTab {

        override val titleResource: Int
            get() = R.string.my_accounts
        override val iconResource: Int
            get() = R.drawable.icon_my_accounts
    }

    data object Settings : MainClientTab {

        override val titleResource: Int
            get() = R.string.settings
        override val iconResource: Int
            get() = R.drawable.icon_settings

    }
}
